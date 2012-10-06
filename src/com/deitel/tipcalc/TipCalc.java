package com.deitel.tipcalc;

import android.app.Activity;
import android.os.Bundle;
//import android.view.Menu;
//import android.widget.EditText;
import android.widget.*;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.text.Editable; 
import android.text.TextWatcher;

public class TipCalc extends Activity {
	private static final String BILL_TOTAL = "BILL_TOTAL"; //for when saving/restoring state
	private static final String CUSTOM_PERCENT = "CUSTOM_PERCENT"; 
	private double currentBillTotal; //bill amt entered by user
	private int currentCustomPercent; //seekbar's value
	private EditText tip10EditText; //shows 10% tip value
	private EditText total10EditText; //shows total amount with 10% tip
	private EditText tip15EditText;
	private EditText total15EditText;
	private EditText tip20EditText;
	private EditText total20EditText;
	private EditText billEditText; //gets input of bill total from user
	private TextView customTipTextView; //shows custom tip percentage of seekbar
	private EditText tipCustomEditText; //gets input of tip that user wants to pay
	private EditText totalCustomEditText; //displays total amount with custom tip value
	private SeekBar customSeekBar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip_calc);
        
        if (savedInstanceState == null) //if application has just started running
        {
        	//display initial values
        	currentBillTotal = 0.0; 
        	currentCustomPercent = 18;
        }
        
        else //if app is being restored from memory
        {
        	//get saved values to display
        	currentBillTotal = savedInstanceState.getDouble(BILL_TOTAL); 
        	currentCustomPercent = savedInstanceState.getInt(CUSTOM_PERCENT);
        }
        
        //get references for the following variables declared
        tip10EditText = (EditText) findViewById(R.id.tip10EditText);
        total10EditText = (EditText) findViewById(R.id.total10EditText);
        tip15EditText = (EditText) findViewById(R.id.tip15EditText);
        total15EditText = (EditText) findViewById(R.id.total15EditText);
        tip20EditText = (EditText) findViewById(R.id.tip20EditText);
        total20EditText = (EditText) findViewById(R.id.total20EditText);
        customTipTextView = (TextView) findViewById(R.id.customTipTextView);
        tipCustomEditText = (EditText) findViewById(R.id.tipCustomTextView);
        totalCustomEditText = (EditText) findViewById(R.id.totalCustomEditText);
        billEditText = (EditText) findViewById(R.id.billEditText);
        customSeekBar = (SeekBar) findViewById(R.id.customSeekBar);
        
		//add a watcher to listen to text change and handle onTextChange event
        billEditText.addTextChangedListener(billEditTextWatcher); 
        
        //add a listener to listen to changes and handle onProgressChanged event
        customSeekBar.setOnSeekBarChangeListener(customSeekBarListener);
        
    } //end onCreate

    //update 10%, 15%, 20% tip EditText boxes 
    private void updateStandard()
    {
    	//calculate bill total with a 10% tip
    	double tenPercentTip = currentBillTotal * 0.1;
    	double tenPercentTotal = currentBillTotal + tenPercentTip;
    	
    	//display tip
    	tip10EditText.setText(String.format("%0.2f", tenPercentTip));
    	//display total amt
    	total10EditText.setText(String.format("%0.2f", tenPercentTotal));
    	
     	//calculate bill total with a 15% tip
    	double fifteenPercentTip = currentBillTotal * 0.15;
    	double fifteenPercentTotal = currentBillTotal + fifteenPercentTip;
    	
    	//display tip
    	tip15EditText.setText(String.format("%0.2f", fifteenPercentTip));
    	//display total amt
    	total15EditText.setText(String.format("%0.2f", fifteenPercentTotal));
    	
     	//calculate bill total with a 20% tip
    	double twentyPercentTip = currentBillTotal * 0.2;
    	double twentyPercentTotal = currentBillTotal + twentyPercentTip;
    	
    	//display tip
    	tip20EditText.setText(String.format("%0.2f", twentyPercentTip));
    	//display total amt
    	total20EditText.setText(String.format("%0.2f", twentyPercentTotal));
    } //end updateStandard
    
    //update customTip and customTotal EditTexts
    private void updateCustom()
    {
    	//set customTipTextView to display the value of the seekbar
    	customTipTextView.setText(currentCustomPercent + "%");
    	//calculate custom tip amt
    	double customTipAmount = currentBillTotal * currentCustomPercent * 0.01;
    	//calculate total amt
    	double customTotalAmount = currentBillTotal + customTipAmount;
    	//display custom tip and corresponding total amt
    	tipCustomEditText.setText(String.format("%0.f", customTipAmount));
    	totalCustomEditText.setText(String.format("%0.2f", customTotalAmount));
    }//end updateCustom
    
    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
    	super.onSaveInstanceState(outState);
    	outState.putDouble(BILL_TOTAL, currentBillTotal);
    	outState.putInt(CUSTOM_PERCENT, currentCustomPercent);
    } //end onSaveInstanceState
    
    //when position of seekbar changed by user
    private OnSeekBarChangeListener customSeekBarListener =
    		new OnSeekBarChangeListener()
    {
    	//when progress changed, update currentCustomPercent and call updateCustom
    	@Override
    	public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
    	{
    		currentCustomPercent = seekBar.getProgress();
    		updateCustom();
    	}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) 
		{			
		}

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) 
		{	
		}
    }; //end OnSeekBarChangeListener
    
    //call when billEditText is changed. ie. user enters new bill amount
    private TextWatcher billEditTextWatcher = new TextWatcher()
    {
    	@Override //when user enters input
    	public void onTextChanged(CharSequence s, int start, int before, int count)
    	{
    		try //try converting input to a double
    		{
    			currentBillTotal = Double.parseDouble(s.toString());
    		}
    		catch (NumberFormatException e) //if fails, set currentBillTotal to 0.0
    		{
    			currentBillTotal = 0.0;
    		}
    		
    		updateStandard();
    		updateCustom();
    	}

		@Override
		public void afterTextChanged(Editable arg0) 
		{
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) 
		{
		}
    }; //end billEditTextWatcher
    
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_tip_calc, menu);
//        return true;
//    }
} //end TipCalc class
