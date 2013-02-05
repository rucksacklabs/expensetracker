package de.reneruck.expensetracker;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;

import de.reneruck.expensetracker.model.ExpenseEntry;

/**
 * Fragment that Provides a list of already used Descriptions as well as an
 * input to dynamically add a new entry description.
 * 
 * @author Rene
 * 
 */
public class FragmentChooseDescription extends SherlockFragment {

	private static final String TAG = "FragmentChooseDescription";
	private ExpenseEntry currentEntry;
	private AppContext context;
	private View layout;

	public FragmentChooseDescription() {
	}
	
	public FragmentChooseDescription(AppContext context, ExpenseEntry currentEntry) {
		this.context = context;
		this.currentEntry = currentEntry;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		this.layout = inflater.inflate(R.layout.new_entry_choose_description, container, false);
		ListView descriptionList = (ListView) this.layout.findViewById(R.id.predefined_description);
		descriptionList.setOnItemSelectedListener(this.onEntrySelectListener);
		((ImageView) this.layout.findViewById(R.id.button_new_description)).setOnClickListener(this.onAddNewDescriptionListener);
		fillDescriptionList(descriptionList);
		return this.layout;
	}
	
	private OnItemSelectedListener onEntrySelectListener = new OnItemSelectedListener() {

		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			// TODO Auto-generated method stub
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
	};
	
	private OnClickListener onAddNewDescriptionListener = new OnClickListener() {
		
		public void onClick(View v) {
			safeNewDescription();
		}
	};
	
	private void safeNewDescription() {
		if(this.layout != null) {
			View input = this.layout.findViewById(R.id.new_description_input);
			if(input != null){
				String newDescriptionText = ((EditText) input).getText().toString();
				if(newDescriptionText.length() > 1) {
					this.currentEntry.setDescription(newDescriptionText);
					InputMethodManager imm = (InputMethodManager) this.context.getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
					// TODO color text input
				}
			}
		}
	}
	
	private void fillDescriptionList(ListView descriptionList) {
		List<String> categories = getStoredDescriptions();
		if(descriptionList != null) {
			descriptionList.setAdapter(new ArrayAdapter<String>(getSherlockActivity(), android.R.layout.simple_selectable_list_item, categories));
		}
	}

	private List<String> getStoredDescriptions() {
		SharedPreferences preferences = getSherlockActivity().getSharedPreferences(AppContext.PREF_USER, Context.MODE_PRIVATE);
		return getList(preferences.getString(AppContext.PREF_USER_DESCRIPTIONS, ""));
	}

	private List<String> getList(String string) {
		if(string.length() > 0) {
			String[] split = string.split(";");
			return Arrays.asList(split);
		}
		return new LinkedList<String>();
	}
}
