package com.zii.lottiecollection;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

  private LinearLayout llContainer;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    llContainer = findViewById(R.id.ll_container);

    addContent();
  }

  private void addContent() {

    Field[] fields = R.raw.class.getDeclaredFields();
    if (fields == null || fields.length == 0) {
      Toast.makeText(this, "NO RAW !!!", Toast.LENGTH_LONG).show();
      Log.e("zii-yunmai", "addContent: " + "NO RAW !!!");
      return;
    }
    String rawName = "";
    int rawId = 0;
    for (Field field : fields) {
      try {
        rawName = field.getName();
        rawId = field.getInt(R.raw.class);
        addItem(rawName, rawId);
        Log.d("zii-yunmai", "Add:" + rawName);
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }
    //playAnimation();
  }

  private void addItem(String rawName, int rawId) {
    TextView nameTv = createNameTv(rawName);
    LottieAnimationView lottie = createLottie(rawId);
    llContainer.addView(createLine());
    llContainer.addView(nameTv);
    llContainer.addView(lottie);
  }

  private View createLine() {
    View view = new View(this);
    ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3);
    params.topMargin = 24;
    params.bottomMargin = 24;
    view.setLayoutParams(params);
    view.setBackgroundColor(Color.parseColor("#4e000000"));
    return view;
  }

  private LottieAnimationView createLottie(int rawId) {
    LottieAnimationView view = new LottieAnimationView(this);
    view.setLayoutParams(new ViewGroup.LayoutParams(450, 450));
    view.setAnimation(rawId);
    view.setRepeatCount(LottieDrawable.INFINITE);
    view.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        LottieAnimationView animationView = (LottieAnimationView) v;
        if (animationView.isAnimating()) {
          animationView.cancelAnimation();
        } else {
          animationView.playAnimation();
        }
      }
    });
    return view;
  }

  private TextView createNameTv(String rawName) {
    TextView tv = new TextView(this);
    tv.setLayoutParams(
        new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    tv.setText(rawName + " : ");
    return tv;
  }

  private void playAnimation() {
    for (int i = 0; i < llContainer.getChildCount(); i++) {
      View child = llContainer.getChildAt(i);
      if (child instanceof LottieAnimationView) {
        ((LottieAnimationView) child).playAnimation();
      }
    }
  }
}
