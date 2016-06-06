package sergio1tsov.english_quiz.com.englishquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


public class GrammarActivity extends Activity {

    private ListView mylistview;
    private ArrayAdapter<String> listAdapter;
    private TextView header;
    private TextView btnMenu;
    private TextView btnQuiz;



    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void createCutomActionBarTitle(){
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(GrammarActivity.this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/freehand.ttf");
        TextView frag1 = (TextView)v.findViewById(R.id.titleFragment1);
        frag1.setTypeface(tf);
        TextView frag2 = (TextView)v.findViewById(R.id.titleFragment2);
        frag2.setTypeface(tf);

        frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GrammarActivity.this, MainMenuActivity.class));
                finish();
            }
        });
        frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GrammarActivity.this, MainMenuActivity.class));
                finish();
            }
        });

        //assign the view to the actionbar
        this.getActionBar().setCustomView(v);
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.my_icon);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);
        createCutomActionBarTitle();

 //       createCutomActionBarTitle();

    btnQuiz = (TextView) findViewById(R.id.btnBeginQuiz);
    btnMenu = (TextView) findViewById(R.id.btnMainMenu);

// Font:
        header = (TextView) findViewById(R.id.headerQuiz);
        Typeface teacherFont = Typeface.createFromAsset(getAssets(),"fonts/freehand.ttf");
        header.setTypeface(teacherFont);
        btnQuiz.setTypeface(teacherFont);
        btnMenu.setTypeface(teacherFont);


// List:
        mylistview = (ListView) findViewById(R.id.lvGrammar);

        listAdapter = new ArrayAdapter<String>(this,
                R.layout.list_white_text, R.id.list_content, getResources().getStringArray(R.array.arrGrammar));
        mylistview.setAdapter(listAdapter);

//        mylistview.setTextColor(color.parseColor("#F12345"));

            }




//////




    public void btnBeginQuiz (View v) {startActivity(new Intent(GrammarActivity.this, QuizAnswerActivity.class));}
    public void mainMenu (View v) {startActivity(new Intent(GrammarActivity.this, MainMenuActivity.class));}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.grammar, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
