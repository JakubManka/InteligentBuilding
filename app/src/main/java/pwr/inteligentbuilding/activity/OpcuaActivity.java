package pwr.inteligentbuilding.activity;

import static org.opcfoundation.ua.utils.EndpointUtil.selectByProtocol;
import static org.opcfoundation.ua.utils.EndpointUtil.sortBySecurityLevel;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.opcfoundation.ua.application.Client;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.Variant;
import org.opcfoundation.ua.core.Attributes;
import org.opcfoundation.ua.core.EndpointDescription;
import org.opcfoundation.ua.core.ReadResponse;
import org.opcfoundation.ua.core.TimestampsToReturn;
import org.opcfoundation.ua.core.WriteResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pwr.inteligentbuilding.BuildConfig;
import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadCreateSession;
import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadDiscoveryEndpoints;
import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadRead;
import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadWrite;
import pwr.inteligentbuilding.OpcUtils.ManagerOPC;
import pwr.inteligentbuilding.OpcUtils.SessionElement;
import pwr.inteligentbuilding.R;

public class OpcuaActivity extends AppCompatActivity {

    EditText edtURL;
    Button btnConnects;
    ManagerOPC manager;
    ProgressDialog dialog;
    EndpointDescription[] endpoints;
    List<EndpointDescription> endpoints_list;
    String url;
    SessionElement sessionElement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcua);

        File certFile = new File(getFilesDir(), "OPCCert.der");
        File privKeyFile = new File(getFilesDir(), "OPCCert.pem");

        manager = ManagerOPC.CreateManagerOPC(certFile, privKeyFile);

        edtURL = findViewById(R.id.edtURL);
        btnConnects = findViewById(R.id.btnConnect);

        endpoints_list = new ArrayList<>();

        btnConnects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = "192.168.8.113:4840";
                System.out.println(url);
                endpoints_list.clear();
                if (!edtURL.getText().toString().toLowerCase().startsWith("opc.tcp://"))
                    url = "opc.tcp://" + edtURL.getText().toString();
                else
                    url = edtURL.getText().toString();

                url = "opc.tcp://192.168.8.113:4840";
                Client client = manager.getClient();
                ThreadDiscoveryEndpoints t = new ThreadDiscoveryEndpoints(client, url);

                @SuppressLint("HandlerLeak") Handler handler_discovery = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == -1) {
                            Toast.makeText(getApplicationContext(), "Endpoints not found" + ((StatusCode) msg.obj).getDescription()
                                    + "\nCode: " + ((StatusCode) msg.obj).getValue().toString(), Toast.LENGTH_LONG).show();
                        } else if (msg.what == -2) {
                            Toast.makeText(getApplicationContext(), "Request timeout", Toast.LENGTH_LONG).show();
                        } else {
                            endpoints = selectByProtocol(sortBySecurityLevel((EndpointDescription[]) msg.obj), "opc.tcp");
                            endpoints_list.addAll(Arrays.asList(endpoints));
                        }
                    }
                };
                t.start(handler_discovery);

            }
        });
    }

    public void createSession() {
        ThreadCreateSession t = new ThreadCreateSession(manager, url, endpoints[0]);
        dialog = ProgressDialog.show(OpcuaActivity.this, "connection attempt", "Session creation", true);
        @SuppressLint("HandlerLeak") Handler handler_createsession = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                dialog.dismiss();
                if (msg.what == -1) {
                    Toast.makeText(getApplicationContext(), "the session could not be created"
                            + ((StatusCode) msg.obj).getDescription() + "\nCode: " + ((StatusCode) msg.obj).getValue().toString(), Toast.LENGTH_LONG).show();
                } else if (msg.what == -2) {
                    Toast.makeText(getApplicationContext(), "Reqest timeout", Toast.LENGTH_LONG).show();
                } else {
                    int session_position = (int) msg.obj;
//                    Intent intent = new Intent(OpcuaActivity.this, SessionActivity.class);
//                    intent.putExtra("sessionPosition", session_position);
//                    intent.putExtra("url", manager.getSessions().get(session_position).getUrl());
//                    startActivity(intent);
                    System.out.println("chyba dziala");
                }
            }
        };
        t.start(handler_createsession);
//        dialogInterface.dismiss();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (SessionElement session : manager.getSessions()) {
            session.getSession().closeAsync();
        }
        manager.getSessions().clear();
    }


    public void handleRead(View view) {
        int namespace = 2;
        int nodeid = 2;
        sessionElement = manager.getSessions().get(0);
        ThreadRead t = new ThreadRead(sessionElement.getSession(), 0, TimestampsToReturn.Both, namespace, nodeid, Attributes.Value);
        ProgressDialog progressDialog = ProgressDialog.show(OpcuaActivity.this, "connecting attempt", "Reading in progress", true);
        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == -1) {
                    Toast.makeText(getApplicationContext(), "Reading not performed" + ((StatusCode) msg.obj).getDescription() + "\nCode: " + ((StatusCode) msg.obj).getValue().toString(), Toast.LENGTH_LONG).show();
                } else if (msg.what == -2) {
                    Toast.makeText(getApplicationContext(), "Request timeoit", Toast.LENGTH_LONG).show();
                } else {
                    ReadResponse res = (ReadResponse) msg.obj;
                    System.out.println(res.getResults()[0].getValue().getValue());
                    System.out.println(res.getResults()[0].getValue());
//                    listReadings.add(res);
//                    adapter.notifyDataSetChanged();
//                    listRead.setSelection(adapter.getCount() - 1);
                }
                progressDialog.dismiss();
            }
        };
        t.start(handler);
        dialog.dismiss();
    }

    public void handleWrite(View view) {
        ThreadWrite t;
        ProgressDialog progressDialog = ProgressDialog.show(OpcuaActivity.this, "Connection attempt", "Writing", true);

        int namespace = 2;
        int nodeid = 2;
        Variant value_write = new Variant("2");
        sessionElement = manager.getSessions().get(0);
        t = new ThreadWrite(sessionElement.getSession(), namespace, nodeid, Attributes.Value, value_write);

        @SuppressLint("HandlerLeak") Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                progressDialog.dismiss();
                if (msg.what == -1) {
                    Toast.makeText(getApplicationContext(), "Write operation failed" + ((StatusCode) msg.obj).getDescription() + "\nCode: " + ((StatusCode) msg.obj).getValue().toString(), Toast.LENGTH_LONG).show();
                } else if (msg.what == -2) {
                    Toast.makeText(getApplicationContext(), "Request timeout", Toast.LENGTH_LONG).show();
                } else {
                    WriteResponse res = (WriteResponse) msg.obj;
                    String response = res.getResults()[0].getDescription();
                    if (response.length() > 0)
                        response = "\n" + res.getResults()[0].getDescription();
                    Toast.makeText(getApplicationContext(), "values sent" + response, Toast.LENGTH_LONG).show();
                }
            }
        };
        t.start(handler);
    }

    public void handleSession(View view) {
        createSession();
    }
}
