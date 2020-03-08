/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csvmanipapp;

/**
 *
 * @author Bolarinwa
 */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import com.opencsv.*;

public class CSVFile {
    
    private String fileName;
    private final HashMap<Integer,List<String>> columns= new HashMap<Integer,List<String>>();
    public final HashMap<Integer,List<String>> sortedRows= new HashMap<Integer,List<String>>();
    private final HashMap<Integer,Double> Fil_averageMeasure = new HashMap<Integer,Double>();
    private final HashMap<Integer,Double> Fil_Nplus3Db = new HashMap<Integer,Double>();
    private final HashMap<Integer,Double> Fil_Nplus5Db = new HashMap<Integer,Double>();
    private final HashMap<Integer,Double> Fil_Nplus10Db = new HashMap<Integer,Double>();
    private List <String[]> wholeData;
    
    public CSVFile(String fileName) {
    	try{
        this.fileName= fileName;
        CSVReader reader = new CSVReader(new FileReader(this.fileName));
        wholeData= reader.readAll();
        reader.close();
    	}
    	catch (Exception ex){
    		ex.printStackTrace();
    	}
    }
    
    public int getNumberOfRows(){
    	return this.wholeData.size();
    }
    
    public  int getNumberOfColumns(){
       
        return this.wholeData.get(0).length;
    }
    
    public void populateColumns(){
        for(int i=0; i<this.getNumberOfColumns();i++){
        	List<String> temp = new ArrayList<String>();
        	for(int j=0; j<this.getNumberOfRows();j++){
        		String [] data =wholeData.get(j);
        		temp.add(data[i]);
        	}
        	sort(temp);
        	columns.put(i, temp);
        }
        
    }
    
    public void sort(List<String> lst){
    	Collections.sort(lst, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				if (Double.parseDouble(o1) < Double.parseDouble(o2)) {
					return 1;
				} else if (Double.parseDouble(o1) > Double.parseDouble(o2)) {
					return -1;
				}
				return 0;
			}
		});
    }
    public List<String> getColumn(int i){
    	return this.columns.get(i);	
    }
    
    public void displayWholeData(){
    	for(String [] s : this.wholeData)
    		System.out.println(Arrays.toString(s));
    	
    }
    
    public void calculate(double N) {
    	calculateWithAverage();
        calculateWithNplus3db(N);
        calculateWithNplus5db(N);
        calculateWithNplus10db(N);
        
    }
    
    public void calculateWithAverage() {
        
    	for(int i =0; i<this.getNumberOfColumns();i++){	
    		int no_of_values_greater_than=0;
                double total=0.0;
                double average;
    		List<String> currentColumn= this.getColumn(i);
                
                //Loop through the values in the current column to get the total
                for(String val : currentColumn)
                    total+= Double.parseDouble(val);
                
                //Compute average of the current column
                average = total / (double)this.getNumberOfRows();
                
    		//loop through values in the current column and get count of numbers greater than average
    		for(String val : currentColumn) {
    			if(Double.parseDouble(val)>(average))
    				no_of_values_greater_than++;
    		}
    		//calculate fil and save file for column i in the HashMap<Integer,Double> fil
    		double fil= (double)(no_of_values_greater_than)/this.getNumberOfRows() * 100;
    		this.Fil_averageMeasure.put(i, fil);
    	}
    }
    
    public void calculateWithNplus3db(double N) {
        double lambda = N + 3.0;
    	for(int i =0; i<this.getNumberOfColumns();i++){	
    		int no_of_values_greater_than=0;
    		List<String> currentColumn= this.getColumn(i);
    		//loop through values in the current column
    		for(String val : currentColumn) {
    			if(Double.parseDouble(val)>(lambda))
    				no_of_values_greater_than++;
    		}
    		//calculate fil and save file for column i in the HashMap<Integer,Double> fil
    		double fil= (double)(no_of_values_greater_than)/this.getNumberOfRows() * 100;
    		this.Fil_Nplus3Db.put(i, fil);
    	}
    }
    
    public void calculateWithNplus5db(double N) {
    	double lambda = N + 5.0;
    	for(int i =0; i<this.getNumberOfColumns();i++){	
    		int no_of_values_greater_than=0;
    		List<String> currentColumn= this.getColumn(i);
    		//loop through values in the current column
    		for(String val : currentColumn) {
    			if(Double.parseDouble(val)>(lambda))
    				no_of_values_greater_than++;
    		}
    		//calculate fil and save file for column i in the HashMap<Integer,Double> fil
    		double fil= (double)(no_of_values_greater_than)/this.getNumberOfRows() * 100;
    		this.Fil_Nplus5Db.put(i, fil);
    	}
    }
    
    public void calculateWithNplus10db(double N) {
    	double lambda = N + 10.0;
    	for(int i =0; i<this.getNumberOfColumns();i++){	
    		int no_of_values_greater_than=0;
    		List<String> currentColumn= this.getColumn(i);
    		//loop through values in the current column
    		for(String val : currentColumn) {
    			if(Double.parseDouble(val)>(lambda))
    				no_of_values_greater_than++;
    		}
    		//calculate fil and save file for column i in the HashMap<Integer,Double> fil
    		double fil= (double)(no_of_values_greater_than)/this.getNumberOfRows() * 100;
    		this.Fil_Nplus10Db.put(i, fil);
    	}
    }
    
    public double getFil_averageMeasure(int i) {
    	return (double)this.Fil_averageMeasure.get(i);	
    }
    
    public double getFil_Nplus3Db(int i) {
    	return (double)this.Fil_Nplus3Db.get(i);	
    }
    
    public double getFil_Nplus5Db(int i) {
    	return (double)this.Fil_Nplus5Db.get(i);	
    }
    
    public double getFil_Nplus10Db(int i) {
    	return (double)this.Fil_Nplus10Db.get(i);	
    }
    
    private void populateSortedRows() {
    	for(int i= 0; i<this.getNumberOfRows();i++){
    		List<String> temp = new ArrayList<String>();
    		for(int j=0; j<this.getNumberOfColumns();j++){
    			List<String> currentColumn= this.getColumn(j);
    			temp.add(currentColumn.get(i));
    		}
    		this.sortedRows.put(i, temp);
    	}
    }
    public void writeCsvFile(String fileName) throws Exception{
    	this.populateSortedRows();
    	FileWriter filewriter = new FileWriter(fileName);
		for(int j=0;j<this.getNumberOfRows();j++){
			for(int i=0; i< this.sortedRows.get(j).size()-1;i++){
				String s = this.sortedRows.get(j).get(i);
				filewriter.append(s);
				filewriter.append(",");
			}
			filewriter.append(this.sortedRows.get(j).get(this.sortedRows.get(j).size()-1));
			filewriter.append("\n");
			
    }
		filewriter.append("\n");
                double total=0.0;
                double average=0.0;
                
		//Append the row of fil gotten with Nplus3Db
		for(int col =0; col<this.getNumberOfColumns()-1;col++){	
                    total+=this.getFil_Nplus3Db(col);
			filewriter.append(String.valueOf(this.getFil_Nplus3Db(col)));
			filewriter.append(",");
		}
                //Add the last column's fil
                filewriter.append(String.valueOf(this.getFil_Nplus3Db(this.getNumberOfColumns()-1)));
                filewriter.append("\n");
                //average= total/(double)this.getNumberOfColumns();
                //filewriter.append(",");
                //filewriter.append(" ");
                
                //filewriter.append(",");
                //filewriter.append(String.valueOf(average));
                
                //Append the row of fil gotten with Nplus5Db
		for(int col =0; col<this.getNumberOfColumns()-1;col++){	
                    total+=this.getFil_Nplus5Db(col);
			filewriter.append(String.valueOf(this.getFil_Nplus5Db(col)));
			filewriter.append(",");
		}
                //Add the last column's fil
                filewriter.append(String.valueOf(this.getFil_Nplus5Db(this.getNumberOfColumns()-1)));
                filewriter.append("\n");
                
                //Append the row of fil gotten with Nplus10Db
		for(int col =0; col<this.getNumberOfColumns()-1;col++){	
                    total+=this.getFil_Nplus10Db(col);
			filewriter.append(String.valueOf(this.getFil_Nplus10Db(col)));
			filewriter.append(",");
		}
                //Add the last column's fil
                filewriter.append(String.valueOf(this.getFil_Nplus10Db(this.getNumberOfColumns()-1)));
                filewriter.append("\n");
                
                //Append the row of fil gotten with average measure
		for(int col =0; col<this.getNumberOfColumns()-1;col++){	
                    total+=this.getFil_averageMeasure(col);
			filewriter.append(String.valueOf(this.getFil_averageMeasure(col)));
			filewriter.append(",");
		}
                //Add the last column's fil
                filewriter.append(String.valueOf(this.getFil_averageMeasure(this.getNumberOfColumns()-1)));
                
		filewriter.flush();
		filewriter.close();
    
    }
    }
 

