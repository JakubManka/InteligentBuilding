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
import org.opcfoundation.ua.core.EndpointDescription;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pwr.inteligentbuilding.BuildConfig;
import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadCreateSession;
import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadDiscoveryEndpoints;
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
                url = "172.17.112.1:4840";
                System.out.println(url);
                endpoints_list.clear();
                if (!edtURL.getText().toString().toLowerCase().startsWith("opc.tcp://"))
                    url = "opc.tcp://" + edtURL.getText().toString();
                else
                    url = edtURL.getText().toString();

                url = "opc.tcp://172.17.112.1:4840";
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (SessionElement session : manager.getSessions()) {
            session.getSession().closeAsync();
        }
        manager.getSessions().clear();
    }
}
