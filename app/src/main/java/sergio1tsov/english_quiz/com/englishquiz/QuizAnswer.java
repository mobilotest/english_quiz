package sergio1tsov.english_quiz.com.englishquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Random;


// declare variables:

public class QuizAnswer extends Activity {

    AnimationDrawable goodBusAnimation;
    AnimationDrawable badBusAnimation;
    AnimationDrawable rmbrBusAnimation;
    ImageView busGood;
    ImageView busBad;
    ImageView busRmbr;


    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void createCutomActionBarTitle(){
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(QuizAnswer.this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/freehand.ttf");
        TextView frag1 = (TextView)v.findViewById(R.id.titleFragment1);
        frag1.setTypeface(tf);
        TextView frag2 = (TextView)v.findViewById(R.id.titleFragment2);
        frag2.setTypeface(tf);

        frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizAnswer.this, MainMenu.class));
                finish();
            }
        });
        frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizAnswer.this, MainMenu.class));
                finish();
            }
        });
        //assign the view to the actionbar
        this.getActionBar().setCustomView(v);
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.my_icon);
    }


    private String rightOneAnswer;
    private EditText enteredX = null;
    private TextView firstA;
    private TextView secondZ;
    private ImageView bus;

    private Button iDontKnow;
    private Button nextEnter;

//    private TextView txtEnter;
//    private TextView txtDontKnow;
//    private TextView result;
//    private TextView header;
//    static int counter = 0;


//  my 5 times sentence appear method:

    public void myFiveSentenceMethod() {


        String[] firstPart = getResources().getStringArray(R.array.arrSentencesFirstPart);
        String[] secondPart = getResources().getStringArray(R.array.arrSentencesSecondPart);
        String[] rightAnswer = getResources().getStringArray(R.array.arrAnswers);

        Random rand = new Random();
        int value = rand.nextInt(firstPart.length);

        secondZ.setText(secondPart[value]);
        firstA.setText(firstPart[value]);
        rightOneAnswer = rightAnswer[value];

        enteredX.setText("");
//          result.setText("ENTER THE MISSING WORD:");
//          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        hideSoftKeyboard();
        nextEnter.setText("Enter");
        iDontKnow.setText("Don't Know");

    }

// hide keyboard method:

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

// Good bus animation:

    public void busGoodAutomation() {
        busGood = (ImageView) findViewById(R.id.handBubble);
        busGood.setBackgroundResource(R.drawable.animation_good);
        goodBusAnimation = (AnimationDrawable) busGood.getBackground();
    }

    // Bad bus animation:
    public void busBadAutomation() {
        busBad = (ImageView) findViewById(R.id.handBubble);
        busBad.setBackgroundResource(R.drawable.animation_bad);
        badBusAnimation = (AnimationDrawable) busBad.getBackground();
    }

    // Don't Know bus animation:
    public void busRmbrAutomation() {
        busRmbr = (ImageView) findViewById(R.id.handBubble);
        busRmbr.setBackgroundResource(R.drawable.animation_rmmbr);
        rmbrBusAnimation = (AnimationDrawable) busRmbr.getBackground();
    }


// onCreate method:

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answer);
        createCutomActionBarTitle();
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


// find items by id:

        iDontKnow = (Button) findViewById(R.id.btnDontKnow);
        nextEnter = (Button) findViewById(R.id.btnNextEnter);
        firstA = (TextView) findViewById(R.id.txtSentence1);
        secondZ = (TextView) findViewById(R.id.txtSentence2);
        enteredX = (EditText) findViewById(R.id.enterHere);
        bus = (ImageView) findViewById(R.id.busPermanent);
//        txtEnter = (TextView) findViewById(R.id.txtEnterNext);
//        txtDontKnow = (TextView) findViewById(R.id.txtDontKnow);



// assign fonts to some TextViews:

        Typeface teacherFont = Typeface.createFromAsset(getAssets(),"fonts/freehand.ttf");
        iDontKnow.setTypeface(teacherFont);
        nextEnter.setTypeface(teacherFont);

        Typeface kidsFont = Typeface.createFromAsset(getAssets(),"fonts/pupil.ttf");
        firstA.setTypeface(kidsFont);
        secondZ.setTypeface(kidsFont);
        enteredX.setTypeface(kidsFont);
        enteredX.setGravity(Gravity.CENTER);


// First bus (foreground):
//        bus = (ImageView) findViewById(R.id.imgBus);   // ALSO COMMENT TO AVOID SECOND BUS
//        bus.setImageResource(0);
//        bus.setBackgroundResource(R.drawable.bus1);


// Launch the first sentence:
        myFiveSentenceMethod();


// Enter key on soft keyboard

        enteredX.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == event.KEYCODE_ENTER) {
                    nextEnter.callOnClick();
                    return true;
                }
                return false;
            }
        });


// Tap on 'I DON'T KNOW' -> add WORD to the EditView:
        iDontKnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                enteredX.setInputType(InputType.TYPE_TEXT_VARIATION_URI); // optional - sets the keyboard to URL mode

// kill keyboard when enter is pressed
                enteredX.setOnKeyListener(new View.OnKeyListener() {
                    /**
                     * This listens for the user to press the enter button on
                     * the keyboard and then hides the virtual keyboard
                     */
                    public boolean onKey(View arg0, int arg1, KeyEvent event) {
                        // If the event is a key-down event on the "enter" button
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (arg1 == KeyEvent.KEYCODE_ENTER)) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(enteredX.getWindowToken(), 0);
                            return true;
                        }
                        return false;
                    }
                });


                String newText = enteredX.getText().toString();
                if (0 == newText.length()) {
                    enteredX.setText(rightOneAnswer);
                    enteredX.setGravity(Gravity.CENTER);
//                          result.setText("PLEASE REMEMBER! KEEP GOING:");           // Text on the bottom changes
//                          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    bus.setImageResource(R.drawable.bus1);       // Smile changes
                    iDontKnow.setText("Grammar");               // Button changes on 'GRAMMAR' (maybe redundancy)
                    nextEnter.setText("Next");                 // Button changes on 'NEXT'
                    hideSoftKeyboard();

// Animation
                    busRmbr = (ImageView) findViewById(R.id.handBubble);
                    busRmbr.setBackgroundResource(R.drawable.animation_rmmbr);
                    rmbrBusAnimation = (AnimationDrawable) busRmbr.getBackground();
                    rmbrBusAnimation.setCallback(bus);
                    rmbrBusAnimation.setVisible(true, true);
//                    rmbrBusAnimation.setOneShot(true);
                    rmbrBusAnimation.start();
// fade:
                    AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
                    alpha.setDuration(5000);
                    alpha.setFillAfter(true);
                    busRmbr.startAnimation(alpha);
//                  hideSoftKeyboard();

//                            if(busGood != null) {
//                                busGood.setImageResource(0);
//                                busGood.setBackgroundResource(R.drawable.bus1);
//                            }
//                            if(busBad != null) {
//                                busBad.setImageResource(0);
//                                busBad.setBackgroundResource(R.drawable.bus1);
//                            }

                } else {
                    startActivity(new Intent(QuizAnswer.this, Grammar.class));
                }
            }
        });

// Input text and tap on Enter:



        nextEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// Compare strings, If input correct:
                final String[] partOne = getResources().getStringArray(R.array.arrSentencesFirstPart);
                final String[] partTwo = getResources().getStringArray(R.array.arrSentencesSecondPart);
                final String[] partAns= getResources().getStringArray(R.array.arrAnswers);

                String newText = enteredX.getText().toString();
                String str = nextEnter.getText().toString();
                if(str.compareToIgnoreCase("Next") == 0) {


//Slide to the next background:
                    ViewFlipper oldBackgrnd = (ViewFlipper) findViewById(R.id.viewFlipper);
//Set an animation from res/anim: I pick push left out
                    oldBackgrnd.setAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.push_left_out));
                    oldBackgrnd.showNext();

                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

//Slide to the next background:
                    ViewFlipper newBackgrnd = (ViewFlipper) findViewById(R.id.viewFlipper);
//Set an animation from res/anim: I pick push left in
                    newBackgrnd.setAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.push_left_in));
                    newBackgrnd.showNext();



                    Random rand = new Random();
                    int value = rand.nextInt(partOne.length);

//                    secondZ.setText(partTwo[value]);
//                    firstA.setText(partOne[value]);
//                    rightOneAnswer = partAns[value];
//                    txtEnter.setText("Enter");
//                    txtDontKnow.setText("Don't Know");

                    ((TextView)newBackgrnd.getCurrentView().findViewById(R.id.txtSentence1)).setText(partOne[value]);
                    ((TextView)newBackgrnd.getCurrentView().findViewById(R.id.txtSentence2)).setText(partTwo[value]);
                    ((EditText)newBackgrnd.getCurrentView().findViewById(R.id.enterHere)).setText(partAns[value]);
                    ((Button)newBackgrnd.getCurrentView().findViewById(R.id.btnDontKnow)).setText("Don't Know");
                    ((Button)newBackgrnd.getCurrentView().findViewById(R.id.btnNextEnter)).setText("Enter");

                    enteredX.setText("");
//                          result.setText("ENTER THE MISSING WORD:");
//                          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//                          bus.setImageResource(R.drawable.bus1);
                    hideSoftKeyboard();

//                            bus.setImageResource(R.drawable.bus1);
//                    if(busGood != null) {                          // JUST COMMENTED TO AVOID SECOND BUS
//                        busGood.setImageResource(0);
//                        busGood.setBackgroundResource(R.drawable.bus1);
//                    }
//                    if(busBad != null) {
//                        busBad.setImageResource(0);
//                        busBad.setBackgroundResource(R.drawable.bus1);
//                    }


                } else if (newText.compareToIgnoreCase(rightOneAnswer) == 0) {
//                          result.setText("YOU'RE RIGHT! GOOD JOB!");
//                          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//                          bus.setImageResource(R.drawable.bus6);
                    hideSoftKeyboard();
                    iDontKnow.setText("Grammar");     // Button changes on 'GRAMMAR'
                    nextEnter.setText("Next");            // Button changes on 'NEXT'
//                          ImageView goodBus = (ImageView) findViewById(R.id.imgBus);
//                          busGood.setBackgroundResource(R.drawable.bus1);
// Animation
                    busGood = (ImageView) findViewById(R.id.handBubble);
                    busGood.setBackgroundResource(R.drawable.animation_good);
                    goodBusAnimation = (AnimationDrawable) busGood.getBackground();
                    goodBusAnimation.setCallback(bus);
                    goodBusAnimation.setVisible(true, true);
//                    goodBusAnimation.setOneShot(true);
                    goodBusAnimation.start();
// fade:
                    AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
                    alpha.setDuration(5000);
                    alpha.setFillAfter(true);
                    busGood.startAnimation(alpha);

//                  hideSoftKeyboard();

// If input incorrect:
                } else if (0 == newText.length() || newText.compareToIgnoreCase(rightOneAnswer) != 0) {
//                          result.setText("YOU'RE  WRONG! TRY AGAIN:");
//                          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//                          bus.setImageResource(R.drawable.busbad6);
                    hideSoftKeyboard();
                    enteredX.setText("");                      // Input field erases
                    iDontKnow.setText("Don't Know");          // Button changes on 'DON"T KNOW'
                    nextEnter.setText("Enter");              // Button changes on 'ENTER' (maybe redundancy)

//                          busBad.setBackgroundResource(R.drawable.bus1);
// Animation
                    busBad = (ImageView) findViewById(R.id.handBubble);
                    busBad.setBackgroundResource(R.drawable.animation_bad);
                    badBusAnimation = (AnimationDrawable) busBad.getBackground();
                    badBusAnimation.setCallback(bus);
                    badBusAnimation.setVisible(true, true);
//                    badBusAnimation.setOneShot(true);
                    badBusAnimation.start();
// fade:
                    AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
                    alpha.setDuration(5000);
                    alpha.setFillAfter(true);
                    busBad.startAnimation(alpha);
//                  hideSoftKeyboard();
//
// Tap on NEXT -> go to Update sentence on activity:
//                            nextEnter.setOnClickListener(new View.OnClickListener() {
//                                // Count for 5 times of correct answers:
//                                @Override
//                                public void onClick(View v) {
//                                    counter++;
//                                    if (counter < 5) {
//                                        myFiveSentenceMethod();
//                                    } else {
//                                        finish();
//                                    }
//                                }
//                            });


                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz_answer, menu);
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