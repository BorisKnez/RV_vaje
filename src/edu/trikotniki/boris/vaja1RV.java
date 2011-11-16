package edu.trikotniki.boris;

import com.badlogic.gdx.backends.android.AndroidApplication;

public class vaja1RV extends AndroidApplication {
        public void onCreate (android.os.Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                initialize(new Game(), false);
        }
}