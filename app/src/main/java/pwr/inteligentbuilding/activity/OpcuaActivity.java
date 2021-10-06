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

import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadCreateSession;
import pwr.inteligentbuilding.OpcUtils.ConnectionThread.ThreadDiscoveryEndpoints;
import pwr.inteligentbuilding.OpcUtils.ManagerOPC;
import pwr.inteligentbuilding.OpcUtils.SessionElement;
import pwr.inteligentbuilding.R;

public class OpcuaActivity extends AppCompatActivity {

    EditText edtURL;
    Button btnConnects;
    ListView listEndpoints;
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
        listEndpoints = findViewById(R.id.ListEndpoints);

        endpoints_list = new ArrayList<>();
//        adapter = new EndpointsAdapter(getApplicationContext(), R.layout.list_endpoints, endpoints_list);
//        listEndpoints.setAdapter(adapter);

        btnConnects.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endpoints_list.clear();
                if (!edtURL.getText().toString().toLowerCase().startsWith("opc.tcp://"))
                    url = "opc.tcp://" + edtURL.getText().toString();
                else
                    url = edtURL.getText().toString();

                if (url.length() <= 10) {
                    Toast.makeText(getApplicationContext(), R.string.IndirizzoNonValido, Toast.LENGTH_SHORT).show();
                } else {
                    Client client = manager.getClient();
                    dialog = ProgressDialog.show(MainActivity.this, getString(R.string.TentativoDiConnessione), getString(R.string.RichiestaEndpoints), true);
                    ThreadDiscoveryEndpoints t = new ThreadDiscoveryEndpoints(client, url);

                    @SuppressLint("HandlerLeak") Handler handler_discovery = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            dialog.dismiss();
                            if (msg.what == -1) {
                                Toast.makeText(getApplicationContext(), getString(R.string.EndpointsNontrovati) + ((StatusCode) msg.obj).getDescription() + "\nCode: " + ((StatusCode) msg.obj).getValue().toString(), Toast.LENGTH_LONG).show();
                            } else if (msg.what == -2) {
                                Toast.makeText(getApplicationContext(), R.string.ServerDown, Toast.LENGTH_LONG).show();
                            } else {
                                endpoints = selectByProtocol(sortBySecurityLevel((EndpointDescription[]) msg.obj), "opc.tcp");
                                endpoints_list.addAll(Arrays.asList(endpoints));
                            }
                            adapter.notifyDataSetChanged();
                        }
                    };
                    t.start(handler_discovery);
                }
            }
        });


        listEndpoints.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                if (endpoints[position].getEndpointUrl().toLowerCase().startsWith("opc.tcp")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle(R.string.VuoiCollegartiAllEndpoint);
                    builder.setMessage(endpoints[position].getEndpointUrl() + "\n"
                            + "SecurityMode: " + endpoints[position].getSecurityMode() + "\n"
                            + "SecurityLevel: " + endpoints[position].getSecurityLevel()
                    );
                    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            switch (which) {
                                case DialogInterface.BUTTON_POSITIVE:
                                    ThreadCreateSession t = new ThreadCreateSession(manager, url, endpoints[position]);
                                    dialog = ProgressDialog.show(MainActivity.this, getString(R.string.TentativoDiConnessione), getString(R.string.CreazioneSessione), true);
                                    @SuppressLint("HandlerLeak") Handler handler_createsession = new Handler() {
                                        @Override
                                        public void handleMessage(Message msg) {
                                            dialog.dismiss();
                                            if (msg.what == -1) {
                                                Toast.makeText(getApplicationContext(), getString(R.string.SessioneNonCreata) + ((StatusCode) msg.obj).getDescription() + "\nCode: " + ((StatusCode) msg.obj).getValue().toString(), Toast.LENGTH_LONG).show();
                                            } else if (msg.what == -2) {
                                                Toast.makeText(getApplicationContext(), R.string.ServerDown, Toast.LENGTH_LONG).show();
                                            } else {
                                                int session_position = (int) msg.obj;
                                                Intent intent = new Intent(MainActivity.this, SessionActivity.class);
                                                intent.putExtra("sessionPosition", session_position);
                                                intent.putExtra("url", manager.getSessions().get(session_position).getUrl());
                                                startActivity(intent);
                                            }
                                        }
                                    };
                                    t.start(handler_createsession);
                                    dialogInterface.dismiss();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    dialogInterface.dismiss();
                                    break;
                            }
                        }
                    };
                    builder.setPositiveButton(android.R.string.yes, listener);
                    builder.setNegativeButton(android.R.string.no, listener);
                    builder.setCancelable(false);
                    Dialog g = builder.create();
                    g.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle(R.string.nonsupportato);
                    alertDialog.setMessage(getString(R.string.protocollononsupportato));
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.ok),
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_sessions:
                intent = new Intent(MainActivity.this, SelectSessionActivity.class);
                startActivity(intent);
                break;
            case R.id.action_info:
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                final SpannableString s =
                        new SpannableString(getString(R.string.infoMessage));
                Linkify.addLinks(s, Linkify.WEB_URLS);
                alertDialog.setTitle(String.format("%s %s", getString(R.string.info), BuildConfig.VERSION_NAME));
                alertDialog.setMessage(s);
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(android.R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                TextView txtDialog = alertDialog.findViewById(android.R.id.message);
                if (txtDialog != null) {
                    txtDialog.setMovementMethod(LinkMovementMethod.getInstance());
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
