package sergio1tsov.english_quiz.com.englishquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;


public class MainMenu extends Activity {

    private TextView header;

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void createCutomActionBarTitle(){
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(MainMenu.this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/freehand.ttf");
        TextView frag1 = (TextView)v.findViewById(R.id.titleFragment1);
        frag1.setTypeface(tf);
        TextView frag2 = (TextView)v.findViewById(R.id.titleFragment2);
        frag2.setTypeface(tf);

        frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, MainMenu.class));
                finish();
            }
        });
        frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenu.this, MainMenu.class));
                finish();
            }
        });

        //assign the view to the actionbar
        this.getActionBar().setCustomView(v);
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.my_icon);
    }



    /*
    private TextView result;
    private ImageView smiley;
    boolean firstTime = true;
    */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main_menu);
    createCutomActionBarTitle();

// Fonts:
        header = (TextView) findViewById(R.id.headerQuiz);
        Typeface teacherFont = Typeface.createFromAsset(getAssets(),"fonts/freehand.ttf");
        header.setTypeface(teacherFont);

//    exit button
    ImageButton exitOut = (ImageButton) findViewById(R.id.exitImgButton);
    exitOut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            finish();
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
            //System.exit(0);
        }
    });

    }

    public void btnFirst_Grammar (View v){
        startActivity(new Intent(MainMenu.this, Grammar.class));
    }

    public void btnSecond_Quiz (View v){
//        firstTime = false;
        startActivity(new Intent(MainMenu.this, QuizAnswer.class));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
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

/*     @Override
    protected void onResume() {
        result = (TextView) findViewById(R.id.rightOrWrong);
        smiley = (ImageView) findViewById(R.id.imgFace);

       if (!firstTime) {
            result.setText("GOOD  JOB !!!");
            smiley.setImageResource(R.drawable.sad);
        };
    }
*/}
