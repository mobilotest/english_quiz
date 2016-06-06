package sergio1tsov.english_quiz.com.englishquiz;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import java.util.Random;


// declare variables:

public class QuizAnswerActivity extends Activity {

    private final String TAG = QuizAnswerActivity.class.getSimpleName();
    private final String NEXT = "Next";
    private final String ENTER = "Enter";
    private final String DONTKNOW = "Don't Know";
    private final String GRAMMER = "GRAMMER";

    private AnimationDrawable mGoodBusAnimation;
    private AnimationDrawable mBadBusAnimation;
    private AnimationDrawable mRememberBusAnimation;
    private ImageView mBusGoodView;
    private ImageView mBusBadView;
    private ImageView mBusRmbrView;

    private String mRightOneAnswer;
    private EditText mEnteredX = null;
    private TextView mFirstA;
    private TextView mSecondZ;
    private ImageView mBus;

    private Button mDontKnowBtn;
    private Button mNextEnterBtn;

    // Compare strings, If input correct:
    private String[] mPartOne;
    private String[] mPartTwo;
    private String[] mPartAns;

    private ViewFlipper mOldBackgrnd;
    private ViewFlipper mNewBackgrnd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_answer);
        createCutomActionBarTitle();
//        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


        // find items by id:
        mBusGoodView = (ImageView) findViewById(R.id.handBubble);
        mBusBadView = (ImageView) findViewById(R.id.handBubble);
        mBusRmbrView = (ImageView) findViewById(R.id.handBubble);
        mDontKnowBtn = (Button) findViewById(R.id.btnDontKnow);
        mNextEnterBtn = (Button) findViewById(R.id.btnNextEnter);
        mFirstA = (TextView) findViewById(R.id.txtSentence1);
        mSecondZ = (TextView) findViewById(R.id.txtSentence2);
        mEnteredX = (EditText) findViewById(R.id.enterHere);
        mBus = (ImageView) findViewById(R.id.busPermanent);
        mOldBackgrnd = (ViewFlipper) findViewById(R.id.viewFlipper);
        mNewBackgrnd = (ViewFlipper) findViewById(R.id.viewFlipper);

        mPartOne = getResources().getStringArray(R.array.arrSentencesFirstPart);
        mPartTwo = getResources().getStringArray(R.array.arrSentencesSecondPart);
        mPartAns = getResources().getStringArray(R.array.arrAnswers);

        // assign fonts to some TextViews:
        Typeface teacherFont = Typeface.createFromAsset(getAssets(),"fonts/freehand.ttf");
        mDontKnowBtn.setTypeface(teacherFont);
        mNextEnterBtn.setTypeface(teacherFont);

        Typeface kidsFont = Typeface.createFromAsset(getAssets(),"fonts/pupil.ttf");
        mFirstA.setTypeface(kidsFont);
        mSecondZ.setTypeface(kidsFont);
        mEnteredX.setTypeface(kidsFont);
        mEnteredX.setGravity(Gravity.CENTER);


// First mBus (foreground):
//        mBus = (ImageView) findViewById(R.id.imgBus);   // ALSO COMMENT TO AVOID SECOND BUS
//        mBus.setImageResource(0);
//        mBus.setBackgroundResource(R.drawable.bus1);


        // Launch the first sentence:
        myFiveSentenceMethod();


        // Enter key on soft keyboard
        mEnteredX.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO || actionId == event.KEYCODE_ENTER) {
                    mNextEnterBtn.callOnClick();
                    return true;
                }
                return false;
            }
        });


// Tap on 'I DON'T KNOW' -> add WORD to the EditView:
        mDontKnowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mEnteredX.setInputType(InputType.TYPE_TEXT_VARIATION_URI); // optional - sets the keyboard to URL mode

// kill keyboard when enter is pressed
                mEnteredX.setOnKeyListener(new View.OnKeyListener() {
                    /**
                     * This listens for the user to press the enter button on
                     * the keyboard and then hides the virtual keyboard
                     */
                    public boolean onKey(View arg0, int arg1, KeyEvent event) {
                        // If the event is a key-down event on the "enter" button
                        if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                                (arg1 == KeyEvent.KEYCODE_ENTER)) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(mEnteredX.getWindowToken(), 0);
                            return true;
                        }
                        return false;
                    }
                });


                String newText = mEnteredX.getText().toString();
                if (0 == newText.length()) {
                    mEnteredX.setText(mRightOneAnswer);
                    mEnteredX.setGravity(Gravity.CENTER);
//                          result.setText("PLEASE REMEMBER! KEEP GOING:");           // Text on the bottom changes
//                          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                    mBus.setImageResource(R.drawable.bus1);       // Smile changes
                    mDontKnowBtn.setText(GRAMMER);               // Button changes on 'GRAMMAR' (maybe redundancy)
                    mNextEnterBtn.setText(NEXT);                 // Button changes on 'NEXT'
                    hideSoftKeyboard();

// Animation
                    mBusRmbrView.setBackgroundResource(R.drawable.animation_rmmbr);
                    mRememberBusAnimation = (AnimationDrawable) mBusRmbrView.getBackground();
                    mRememberBusAnimation.setCallback(mBus);
                    mRememberBusAnimation.setVisible(true, true);
//                    mRememberBusAnimation.setOneShot(true);
                    mRememberBusAnimation.start();
// fade:
                    AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
                    alpha.setDuration(5000);
                    alpha.setFillAfter(true);
                    mBusRmbrView.startAnimation(alpha);
//                  hideSoftKeyboard();

//                            if(mBusGoodView != null) {
//                                mBusGoodView.setImageResource(0);
//                                mBusGoodView.setBackgroundResource(R.drawable.bus1);
//                            }
//                            if(mBusBadView != null) {
//                                mBusBadView.setImageResource(0);
//                                mBusBadView.setBackgroundResource(R.drawable.bus1);
//                            }

                } else {
                    startActivity(new Intent(QuizAnswerActivity.this, GrammarActivity.class));
                }
            }
        });

// Input text and tap on Enter:



        mNextEnterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d(TAG, "Got Here!!!");
                String newText = mEnteredX.getText().toString();
                String str = mNextEnterBtn.getText().toString();
                if (str.compareToIgnoreCase(NEXT) == 0) {
                    //Slide to the next background:
                    //Set an animation from res/anim: I pick push left out
                    mOldBackgrnd.setAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.push_left_out));
                    mOldBackgrnd.showNext();

                    overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

                    //Slide to the next background:
                    //Set an animation from res/anim: I pick push left in
                    mNewBackgrnd.setAnimation(AnimationUtils.loadAnimation(v.getContext(), R.anim.push_left_in));
                    mNewBackgrnd.showNext();


                    Random rand = new Random();
                    int value = rand.nextInt(mPartOne.length);

//                    mSecondZ.setText(mPartTwo[value]);
//                    mFirstA.setText(mPartOne[value]);
//                    mRightOneAnswer = mPartAns[value];
//                    txtEnter.setText("Enter");
//                    txtDontKnow.setText("Don't Know");

                    mFirstA = (TextView) mNewBackgrnd.getCurrentView().findViewById(R.id.txtSentence1);
                    mFirstA.setText(mPartOne[value]);
                    mSecondZ = (TextView) mNewBackgrnd.getCurrentView().findViewById(R.id.txtSentence2);
                    mSecondZ.setText(mPartTwo[value]);
                    mEnteredX = (EditText) mNewBackgrnd.getCurrentView().findViewById(R.id.enterHere);
                    mEnteredX.setText(mPartAns[value]);
                    mDontKnowBtn = (Button) mNewBackgrnd.getCurrentView().findViewById(R.id.btnDontKnow);
                    mDontKnowBtn.setText(DONTKNOW);
                    mNextEnterBtn = (Button) mNewBackgrnd.getCurrentView().findViewById(R.id.btnNextEnter);
                    mNextEnterBtn.setText(ENTER);

                    mEnteredX.clearComposingText();
//                          result.setText("ENTER THE MISSING WORD:");
//                          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//                          mBus.setImageResource(R.drawable.bus1);
                    hideSoftKeyboard();

//                            mBus.setImageResource(R.drawable.bus1);
//                    if(mBusGoodView != null) {                          // JUST COMMENTED TO AVOID SECOND BUS
//                        mBusGoodView.setImageResource(0);
//                        mBusGoodView.setBackgroundResource(R.drawable.bus1);
//                    }
//                    if(mBusBadView != null) {
//                        mBusBadView.setImageResource(0);
//                        mBusBadView.setBackgroundResource(R.drawable.bus1);
//                    }


                } else if (newText.compareToIgnoreCase(mRightOneAnswer) == 0) {
//                          result.setText("YOU'RE RIGHT! GOOD JOB!");
//                          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//                          mBus.setImageResource(R.drawable.bus6);
                    hideSoftKeyboard();
                    mDontKnowBtn.setText(GRAMMER);     // Button changes on 'GRAMMAR'
                    mNextEnterBtn.setText(NEXT);            // Button changes on 'NEXT'
//                          ImageView goodBus = (ImageView) findViewById(R.id.imgBus);
//                          mBusGoodView.setBackgroundResource(R.drawable.bus1);
// Animation
                    mBusGoodView.setBackgroundResource(R.drawable.animation_good);
                    mGoodBusAnimation = (AnimationDrawable) mBusGoodView.getBackground();
                    mGoodBusAnimation.setCallback(mBus);
                    mGoodBusAnimation.setVisible(true, true);
//                    mGoodBusAnimation.setOneShot(true);
                    mGoodBusAnimation.start();
// fade:
                    AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
                    alpha.setDuration(5000);
                    alpha.setFillAfter(true);
                    mBusGoodView.startAnimation(alpha);

//                  hideSoftKeyboard();

// If input incorrect:
                } else if (0 == newText.length() || newText.compareToIgnoreCase(mRightOneAnswer) != 0) {
//                          result.setText("YOU'RE  WRONG! TRY AGAIN:");
//                          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
//                          mBus.setImageResource(R.drawable.busbad6);
                    hideSoftKeyboard();
                    mEnteredX.clearComposingText();                      // Input field erases
                    mDontKnowBtn.setText(DONTKNOW);          // Button changes on 'DON"T KNOW'
                    mNextEnterBtn.setText(ENTER);              // Button changes on 'ENTER' (maybe redundancy)

//                          mBusBadView.setBackgroundResource(R.drawable.bus1);
// Animation
                    mBusBadView.setBackgroundResource(R.drawable.animation_bad);
                    mBadBusAnimation = (AnimationDrawable) mBusBadView.getBackground();
                    mBadBusAnimation.setCallback(mBus);
                    mBadBusAnimation.setVisible(true, true);
//                    mBadBusAnimation.setOneShot(true);
                    mBadBusAnimation.start();
// fade:
                    AlphaAnimation alpha = new AlphaAnimation(1.0F, 0.0F);
                    alpha.setDuration(5000);
                    alpha.setFillAfter(true);
                    mBusBadView.startAnimation(alpha);
//                  hideSoftKeyboard();
//
// Tap on NEXT -> go to Update sentence on activity:
//                            mNextEnterBtn.setOnClickListener(new View.OnClickListener() {
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

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void createCutomActionBarTitle(){
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        LayoutInflater inflator = LayoutInflater.from(QuizAnswerActivity.this);
        View v = inflator.inflate(R.layout.custom_action_bar, null);

        Typeface tf = Typeface.createFromAsset(getAssets(),"fonts/freehand.ttf");
        TextView frag1 = (TextView)v.findViewById(R.id.titleFragment1);
        frag1.setTypeface(tf);
        TextView frag2 = (TextView)v.findViewById(R.id.titleFragment2);
        frag2.setTypeface(tf);

        frag1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizAnswerActivity.this, MainMenuActivity.class));
                finish();
            }
        });
        frag2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QuizAnswerActivity.this, MainMenuActivity.class));
                finish();
            }
        });
        //assign the view to the actionbar
        this.getActionBar().setCustomView(v);
        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.my_icon);
    }



//  my 5 times sentence appear method:

    public void myFiveSentenceMethod() {

        Random rand = new Random();
        int value = rand.nextInt(mPartOne.length);

        mFirstA.setText(mPartOne[value]);
        mSecondZ.setText(mPartTwo[value]);
        mRightOneAnswer = mPartAns[value];

        mEnteredX.setText("");
//          result.setText("ENTER THE MISSING WORD:");
//          result.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
        hideSoftKeyboard();
        mNextEnterBtn.setText(ENTER);
        mDontKnowBtn.setText(DONTKNOW);

    }

// hide keyboard method:

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

// Good mBus animation:

    public void busGoodAutomation() {
        mBusGoodView = (ImageView) findViewById(R.id.handBubble);
        mBusGoodView.setBackgroundResource(R.drawable.animation_good);
        mGoodBusAnimation = (AnimationDrawable) mBusGoodView.getBackground();
    }

    // Bad mBus animation:
    public void busBadAutomation() {
        mBusBadView = (ImageView) findViewById(R.id.handBubble);
        mBusBadView.setBackgroundResource(R.drawable.animation_bad);
        mBadBusAnimation = (AnimationDrawable) mBusBadView.getBackground();
    }

    // Don't Know mBus animation:
    public void busRmbrAutomation() {
        mBusRmbrView = (ImageView) findViewById(R.id.handBubble);
        mBusRmbrView.setBackgroundResource(R.drawable.animation_rmmbr);
        mRememberBusAnimation = (AnimationDrawable) mBusRmbrView.getBackground();
    }


// onCreate method:

}