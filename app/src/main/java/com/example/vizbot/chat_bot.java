package com.example.vizbot;

import android.Manifest;
import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Environment;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.FirebaseDatabase;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.Graphmaster;
import org.alicebot.ab.MagicBooleans;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;
import org.alicebot.ab.Timer;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class chat_bot extends AppCompatActivity {


    private ListView mListView;
    private EditText mEditTextMessage;
    public Bot bot;
    public static Chat chat;
    private ChatMessageAdapter mAdapter;
    Button mButtonSend;
    String add,FullName,phone,bloodtype,docphone,rl1,rl1_name,rl1_num,rl2,rl2_name,rl2_num,rl3,rl3_name,rl3_num,desp,occup;
    boolean check=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle bundle=getIntent().getExtras();
        add=bundle.getString("add");
        FullName=bundle.getString("FullName");
         phone=bundle.getString("phone");
         docphone=bundle.getString("docphone");
         bloodtype=bundle.getString("bloodtype");
         rl1=bundle.getString("rl1");
         rl1_name=bundle.getString("rl1_name");
         rl1_num=bundle.getString("rl1_num");
         rl2=bundle.getString("rl2");
         rl2_name=bundle.getString("rl2_name");
         rl2_num=bundle.getString("rl2_num");
         rl3=bundle.getString("rl3");
         rl3_name=bundle.getString("rl3_name");
         rl3_num=bundle.getString("rl3_num");
         desp=bundle.getString("desc");
        occup=bundle.getString("occup");
        add.replace(' ','+');
        mListView = (ListView) findViewById(R.id.listView);
        mButtonSend = findViewById(R.id.btn_send);
        mEditTextMessage = (EditText) findViewById(R.id.et_message);
        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mListView.setAdapter(mAdapter);
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(check){
                    chat.multisentenceRespond("FullName "+FullName);
                    chat.multisentenceRespond("phone "+phone);
                    chat.multisentenceRespond("docphone "+docphone);
                    chat.multisentenceRespond("add "+add);
                    chat.multisentenceRespond("bloodtype "+bloodtype);
                    chat.multisentenceRespond("rl1 "+rl1);
                    chat.multisentenceRespond("rl1_name "+rl1_name);
                    chat.multisentenceRespond("rl1_num "+rl1_num);
                    chat.multisentenceRespond("rl2 "+rl2);
                    chat.multisentenceRespond("rl2_name "+rl2_name);
                    chat.multisentenceRespond("rl2_num "+rl2_num);
                    chat.multisentenceRespond("rl3 "+rl3);
                    chat.multisentenceRespond("rl3_name "+rl3_name);
                    chat.multisentenceRespond("rl3_num "+rl3_num);
                    chat.multisentenceRespond("desp "+desp);
                    chat.multisentenceRespond("occup "+occup);
                    check=false;
                }
                String message = mEditTextMessage.getText().toString();
                String response = chat.multisentenceRespond(mEditTextMessage.getText().toString());
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                if(response.equals("opening maps")){
                    Uri gmmIntentUri = Uri.parse("google.navigation:q="+add);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    startActivity(mapIntent);
                }
                if(response.equals("calling "+rl1_name)){
                    Intent myDialIntent = new Intent(Intent.ACTION_DIAL);
                    myDialIntent.setData(Uri.parse("tel:"+rl1_num));
                    startActivity(myDialIntent);
                }
                if(response.equals("calling "+rl2_name)){
                    Intent myDialIntent = new Intent(Intent.ACTION_DIAL);
                    myDialIntent.setData(Uri.parse("tel:"+rl2_num));
                    startActivity(myDialIntent);
                }
                if(response.equals("calling "+rl3_name)){
                    Intent myDialIntent = new Intent(Intent.ACTION_DIAL);
                    myDialIntent.setData(Uri.parse("tel:"+rl3_num));
                    startActivity(myDialIntent);
                }
                if(response.equals("calling doctor")){
                    Intent myDialIntent = new Intent(Intent.ACTION_DIAL);
                    myDialIntent.setData(Uri.parse("tel:"+docphone));
                    startActivity(myDialIntent);
                }
                if(response.equals("calling police")){
                    Intent myDialIntent = new Intent(Intent.ACTION_DIAL);
                    myDialIntent.setData(Uri.parse("tel:"+"100"));
                    startActivity(myDialIntent);
                }
                if(response.equals("calling ambulance")){
                    Intent myDialIntent = new Intent(Intent.ACTION_DIAL);
                    myDialIntent.setData(Uri.parse("tel:"+"108"));
                    startActivity(myDialIntent);
                }
                FirebaseDatabase.getInstance().getReference().child(message).setValue(response);
                sendMessage(message);
                mimicOtherMessage(response);
                mEditTextMessage.setText("");
                mListView.setSelection(mAdapter.getCount() - 1);
            }
        });
        Dexter.withActivity(this).withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {
                if(report.areAllPermissionsGranted()){
                    custom();
                    Toast.makeText(chat_bot.this, "Permission granted", Toast.LENGTH_SHORT).show();
                }
                if(report.isAnyPermissionPermanentlyDenied()){
                    Toast.makeText(chat_bot.this, "Please grant all permissions", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Toast.makeText(chat_bot.this, "error", Toast.LENGTH_SHORT).show();
            }
        }).onSameThread().check();
    }

    private void custom() {
        boolean a = isSDCARDAvailable();
        AssetManager assets = getResources().getAssets();
        File jayDir = new File(Environment.getExternalStorageDirectory().toString() + "/koi13/bots/koi13");
        boolean b = jayDir.mkdirs();
        if (jayDir.exists()) {
            try {
                for (String dir : assets.list("koi13")) {
                    File subdir = new File(jayDir.getPath() + "/" + dir);
                    boolean subdir_check = subdir.mkdirs();
                    for (String file : assets.list("koi13/" + dir)) {
                        File f = new File(jayDir.getPath() + "/" + dir + "/" + file);
                        if (f.exists()) {
                            continue;
                        }
                        InputStream in = null;
                        OutputStream out = null;
                        in = assets.open("koi13/" + dir + "/" + file);
                        out = new FileOutputStream(jayDir.getPath() + "/" + dir + "/" + file);
                        copyFile(in, out);
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/koi13";
        AIMLProcessor.extension =  new PCAIMLProcessorExtension();
        bot = new Bot("koi13", MagicStrings.root_path, "chat");
        chat = new Chat(bot);
        String[] args = null;
        mainFunction(args);
    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);
    }

    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }
    public static boolean isSDCARDAvailable(){
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)? true :false;
    }
    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }
    public static void mainFunction (String[] args) {
        MagicBooleans.trace_mode = false;
        Graphmaster.enableShortCuts = true;
        Timer timer = new Timer();
        String request = "Hello.";
        String response = chat.multisentenceRespond(request);
    }

}