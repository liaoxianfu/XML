package com.example.liao.xml;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static String XML = "XML";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final List<Person> personList = new LinkedList<Person>();
        personList.add(new Person(1, "12", "张三"));
        personList.add(new Person(2, "13", "李四"));
        Button button = (Button) findViewById(R.id.btn1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createXML(personList);
            }
        });
        Button button1 = (Button) findViewById(R.id.btn2);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPassword();
            }
        });
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (isExternalStorageWritable() || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;

        }
        return false;
    }

    private File makedirs(String dirName) {
        File file = new File(Environment.getExternalStorageDirectory(), dirName);
        if (!file.exists()) {
            file.mkdirs();
        }
        return file;

    }

    private void createXML(List list) {
        //创建外部文件
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "无法获取存储权限", Toast.LENGTH_SHORT).show();
            return;
        }
        String dirNamme = XML;
        File file = new File(makedirs(dirNamme), "person.xml");
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            XmlSerializer serializer = Xml.newSerializer();
            //开始文件流读写
            serializer.setOutput(outputStream, "utf-8");
//            开始文件写入
            serializer.startDocument("utf-8", true);
            serializer.startTag(null, "Persons");
            //开始对标签的写入
            for (Object person : list) {
                Person p = (Person) person;
                serializer.startTag(null, "person");
                serializer.attribute(null, "id", p.getId() + "");
                serializer.startTag(null, "name");
                serializer.text(((Person) person).getName());
                serializer.endTag(null, "name");
                serializer.startTag(null, "age");
                serializer.text(((Person) person).getAge());
                serializer.endTag(null, "age");

                serializer.endTag(null, "person");
            }
            serializer.endTag(null, "Persons");
//            结束文件写入
            serializer.endDocument();
            serializer.flush();
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void createPassword() {
        if (!isExternalStorageWritable()) {
            Toast.makeText(this, "无法创建目录", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(makedirs("password"), "pss");
        //创建文件
        try {
            FileOutputStream outputStreamm = new FileOutputStream(file);
            XmlSerializer serializer = Xml.newSerializer();
            serializer.setOutput(outputStreamm,"utf-8");
            serializer.startDocument("utf-8", true);

            serializer.startTag("password", "password");

            serializer.startTag(null, "name");
            EditText name = (EditText) findViewById(R.id.name);
            /***********用户名**************/
            serializer.text(name.getText().toString().trim());
            serializer.endTag(null, "name");

            serializer.startTag(null, "pass");
            EditText pass = (EditText) findViewById(R.id.pass);
            serializer.text(pass.getText().toString().trim());
            serializer.endTag(null, "pass");

            serializer.endTag("password", "password");

            serializer.endDocument();

            serializer.flush();
            outputStreamm.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
