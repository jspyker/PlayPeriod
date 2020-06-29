package com.rexcola.playperiod;


import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

    /**
     * Displays an Android spinner widget backed by data in an array. The
     * array is loaded from the strings.xml resources file.
     */
    public class MainActivity extends AppCompatActivity {

        private static final int MENU_RESET = 1;

        private static final int MENU_STOP = 2;
        private static final int MENU_HARDER = 3;
        private static final int MENU_EASIER = 4;
        private static final int MENU_SOLVE = 5;
        private int difficulty_level = 0;
        private int increment = 10;
        private int fullIncrement = 100;

        private void setIncrement()
        {
            switch(difficulty_level)
            {
                case 0:
                    increment = 100;
                    break;
                case 1:
                    increment = 50;
                    break;
                case 2:
                    increment = 25;
                    break;
                case 3:
                    increment = 10;
                    break;
            }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            super.onCreateOptionsMenu(menu);

            menu.add(0, MENU_RESET, 0, R.string.menu_reset);
            menu.add(0, MENU_SOLVE,0, R.string.menu_solve);
            menu.add(0, MENU_HARDER, 0, R.string.menu_harder);
            menu.add(0, MENU_EASIER, 0, R.string.menu_easier);
            menu.add(0, MENU_STOP, 0, R.string.menu_stop);

            return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                case MENU_RESET:
                    setInitialState();
                    return true;
                case MENU_SOLVE:
                    solve();
                    return true;
                case MENU_HARDER:
                    makeHarder();
                    setInitialState();
                    return true;
                case MENU_EASIER:
                    makeEasier();
                    setInitialState();
                    return true;
                case MENU_STOP:
                    finish();
                    return true;
            }

            return false;
        }
        private void makeHarder()
        {
            if (difficulty_level < 3)
            {
                difficulty_level += 1;
            }
        }

        private void makeEasier()
        {
            if (difficulty_level > 0)
            {
                difficulty_level -= 1;
            }
        }

        private void solve()
        {
            for (int i = 0; i < editText.length; i++)
            {
                guess[i] = solution[i];
                showGuess(i);
            }
        }

        protected void showGuess(int i)
        {
            double curValue = guess[i]/100.0;
            editText[i].setText(Double.toString(curValue));
        }
        /**
         * Fields to contain the current position and display contents of the spinner
         */
        EditText[] editText;
        PlayPeriodView surfaceView;

        public static final String EDITTEXT = "EditText";

        /**
         * The name of a properties file that stores the position and
         * selection when the activity is not loaded.
         */
        public static final String PREFERENCES_FILE = "PlayPeriodPrefs";

        /**
         * These values are used to read and write the properties file.
         * PROPERTY_DELIMITER delimits the key and value in a Java properties file.
         * The "marker" strings are used to write the properties into the file
         */
        public static final String PROPERTY_DELIMITER = "=";

        /**
         * Initializes the application and the activity.
         * 1) Sets the view
         * 2) Reads the spinner's backing data from the string resources file
         * 3) Instantiates a callback listener for handling selection from the
         *    spinner
         * Notice that this method includes code that can be uncommented to force
         * tests to fail.
         *
         * This method overrides the default onCreate() method for an Activity.
         *
         * @see android.app.Activity#onCreate(android.os.Bundle)
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {

            /**
             * derived classes that use onCreate() overrides must always call the super constructor
             */
            super.onCreate(savedInstanceState);

            setContentView(R.layout.main);
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            editText = new EditText[3];
            editText[0] = (EditText) findViewById(R.id.edittext1);
            editText[1] = (EditText) findViewById(R.id.edittext2);
            editText[2] = (EditText) findViewById(R.id.edittext3);
            solution = new int[3];
            guess = new int[3];


            setInitialState();

            surfaceView = (PlayPeriodView) findViewById(R.id.surfaceView1);
            surfaceView.addGameContext(this);

            /*
             * Create a listener that is triggered when Android detects the
             * user has selected an item in the Spinner.
             */

            TextWatcher myTextWatcher = new com.rexcola.playperiod.MainActivity.MyTextWatcher();
            for (int i = 0; i < editText.length ; i++)
            {
                editText[i].addTextChangedListener(myTextWatcher);
            }
            showGuess(0);

            final Button button1down = (Button) findViewById(R.id.button1down);
            button1down.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    downValue(1);
                }
            });
            final Button button1up = (Button) findViewById(R.id.button1up);
            button1up.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    upValue(1);
                }
            });
            final Button button1downdown = (Button) findViewById(R.id.button1downdown);
            button1downdown.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    downdownValue(1);
                }
            });
            final Button button1upup = (Button) findViewById(R.id.button1upup);
            button1upup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    upupValue(1);
                }
            });
            final Button button2down = (Button) findViewById(R.id.button2down);
            button2down.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    downValue(2);
                }
            });
            final Button button2up = (Button) findViewById(R.id.button2up);
            button2up.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    upValue(2);
                }
            });
            final Button button2downdown = (Button) findViewById(R.id.button2downdown);
            button2downdown.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    downdownValue(2);
                }
            });
            final Button button2upup = (Button) findViewById(R.id.button2upup);
            button2upup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    upupValue(2);
                }
            });
            final Button button3down = (Button) findViewById(R.id.button3down);
            button3down.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    downValue(3);
                }
            });
            final Button button3up = (Button) findViewById(R.id.button3up);
            button3up.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    upValue(3);
                }
            });
            final Button button3downdown = (Button) findViewById(R.id.button3downdown);
            button3downdown.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    downdownValue(3);
                }
            });
            final Button button3upup = (Button) findViewById(R.id.button3upup);
            button3upup.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    upupValue(3);
                }
            });
        }

        private void downValue(int i)
        {
            if (guess[i-1] > 0)
            {
                guess[i-1] -= increment;
            }
            showGuess(i-1);

        }
        private void upValue(int i)
        {
            if (guess[i-1] < 1000)
            {
                guess[i-1] += increment;
            }
            showGuess(i-1);
        }
        private void downdownValue(int i)
        {
            guess[i-1] -= fullIncrement;
            if (guess[i-1] < 0)
            {
                guess[i-1] = 0;
            }
            showGuess(i-1);

        }
        private void upupValue(int i)
        {
            guess[i-1] += fullIncrement;
            if (guess[i-1] > 1000)
            {
                guess[i-1] = 1000;
            }
            showGuess(i-1);

        }

        public class MyTextWatcher implements TextWatcher
        {

            public void afterTextChanged(Editable arg0)
            {
                // For the moment, just total up the values from the first 3 editable text windows
//	        double[] guess = new double[100];
//	        for (int i = 0; i < editText.length; i ++)
//	        {
//	        	Float curValue;
//	        	try {
//					curValue = Float.valueOf(editText[i].getText().toString());
//				} catch (NumberFormatException e) {
//					curValue = Float.valueOf(0);
//				}
//	        	guess[i] = curValue;
//	        }

                SurfaceHolder handler = surfaceView.getHolder();
                Canvas c = handler.lockCanvas();
                if (c != null)
                {
                    drawCurve(c, solution, guess);
                    handler.unlockCanvasAndPost(c);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after)
            {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count)
            {
            }

        }


        @Override
        public void onResume() {

            /*
             * an override to onResume() must call the super constructor first.
             */

            super.onResume();

            /*
             * Try to read the preferences file. If not found, set the state to the desired initial
             * values.
             */

            if (!readInstanceState(this)) setInitialState();
            showGuess(0);

        }

        /**
         * Store the current state of the spinner (which item is selected, and the value of that item).
         * Since onPause() is always called when an Activity is about to be hidden, even if it is about
         * to be destroyed, it is the best place to save state.
         *
         * Attempt to write the state to the preferences file. If this fails, notify the user.
         *
         * @see android.app.Activity#onPause()
         */
        @Override
        public void onPause() {

            /*
             * an override to onPause() must call the super constructor first.
             */

            super.onPause();

            /*
             * Save the state to the preferences file. If it fails, display a Toast, noting the failure.
             */

            if (!writeInstanceState(this)) {
                Toast.makeText(this,
                        "Failed to write state!", Toast.LENGTH_LONG).show();
            }
        }

        int[] solution;
        int[] guess;
        /**
         * Sets the initial state of the spinner when the application is first run.
         */
        public void setInitialState() {

            setIncrement();
            Random myRandomizer = new Random();
            for (int i = 0; i < editText.length; i++)
            {
                editText[i].setText("0.0");
                guess[i] = 0;
                switch (difficulty_level)
                {
                    case 0:
                        int lowRes = 1 + myRandomizer.nextInt(9);
                        solution[i] = lowRes* 100;
                        break;
                    case 1:
                        int medRes = 2 + myRandomizer.nextInt(18);
                        solution[i] = medRes*50;
                        break;
                    case 2:
                        int med2Res = 4 + myRandomizer.nextInt(36);
                        solution[i] = med2Res*25;
                        break;
                    case 3:
                        int highRes = 10 + myRandomizer.nextInt(90);
                        solution[i] = highRes*10;
                        break;
                }
            }

        }

        /**
         * Read the previous state of the spinner from the preferences file
         * @param c - The Activity's Context
         */
        public boolean readInstanceState(Context c) {

            /*
             * The preferences are stored in a SharedPreferences file. The abstract implementation of
             * SharedPreferences is a "file" containing a hashmap. All instances of an application
             * share the same instance of this file, which means that all instances of an application
             * share the same preference settings.
             */

            /*
             * Get the SharedPreferences object for this application
             */

            SharedPreferences p = c.getSharedPreferences(PREFERENCES_FILE, MODE_PRIVATE);
            /*
             * Get the position and value of the spinner from the file, or a default value if the
             * key-value pair does not exist.
             */
            for (int i = 0; i < editText.length; i++)
            {
                editText[1].setText(p.getString(EDITTEXT + i, "0.0"));
            }

            /*
             * SharedPreferences doesn't fail if the code tries to get a non-existent key. The
             * most straightforward way to indicate success is to return the results of a test that
             * SharedPreferences contained the position key.
             */

            return (p.contains("EDITTEXT1"));

        }

        /**
         * Write the application's current state to a properties repository.
         * @param c - The Activity's Context
         *
         */
        public boolean writeInstanceState(Context c) {

            /*
             * Get the SharedPreferences object for this application
             */

            SharedPreferences p =
                    c.getSharedPreferences(com.rexcola.playperiod.MainActivity.PREFERENCES_FILE, MODE_PRIVATE);

            /*
             * Get the editor for this object. The editor interface abstracts the implementation of
             * updating the SharedPreferences object.
             */

            SharedPreferences.Editor e = p.edit();

            /*
             * Write the keys and values to the Editor
             */

            for (int i = 0; i < editText.length; i++)
            {
                e.putString(EDITTEXT+i,editText[i].getText().toString());
            }

            return (e.commit());

        }

        private void drawCurve(Canvas c, int[] solutionPeriods, int[] guessPeriods)
        {
            Paint p = new Paint();
            p.setColor(Color.BLUE);
            Paint black = new Paint();
            black.setColor(Color.BLACK);
            int mHeight = c.getHeight();
            int mWidth = c.getWidth();
            Rect rect = new Rect(0,0,mWidth,mHeight);
            c.drawRect(rect, black);

            if (isSolved())
            {
                Paint green = new Paint();
                green.setColor(Color.GREEN);
                int top = mHeight/2 - 5;
                int bottom = top + 10;
                Rect solvedRect = new Rect(0,top,mWidth,bottom);
                c.drawRect(solvedRect,green);
            }
            else
            {
                int j = mHeight / (editText.length * 4);
                double k2 =  (2.0 * 3.1415926)/ (double) mWidth;

                //Set first point
                float Px = 0;
                float Py = (int) ((double) editText.length * 2 *j);

                for (int i = 1; i < mWidth; i ++)
                {
                    double y = 0;
                    for (int m = 0; m < editText.length; m++)
                    {
                        double solutionAsDouble = solutionPeriods[m] / 100.0;
                        double guessAsDouble = guessPeriods[m]/ 100.0;
                        y = y + Math.sin((double) i * solutionAsDouble * k2);
                        y = y - Math.sin((double) i * guessAsDouble * k2);
                    }

                    float Nx = i;
                    float Ny = (int)( (y + (double) editText.length * 2) * j);
                    c.drawLine (Px, Py,Nx, Ny, p);
                    Px = Nx;
                    Py = Ny;
                }
            }
        }

        private boolean isSolved()
        {
            boolean solved = false;
            int guessTotal = 0;
            int solutionTotal = 0;

            for (int i = 0; i < editText.length; i++)
            {
                guessTotal += guess[i];
                solutionTotal += solution[i];
            }
            if (guessTotal == solutionTotal)
            {
                solved = true;
                // Check that each solution value exists in the guess
                for (int i = 0; i < editText.length; i++)
                {
                    boolean notFound = true;
                    for (int j = 0; j < editText.length; j++)
                    {
                        if (solution[i] == guess[j])
                        {
                            notFound = false;
                        }
                    }
                    if (notFound)
                    {
                        solved = false;
                    }
                }
            }

            return solved;
        }

    }

