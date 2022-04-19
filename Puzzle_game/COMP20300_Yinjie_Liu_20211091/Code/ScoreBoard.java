package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.*;
import java.util.*;
/**
* Class ScoreBoard create and exhibit the scoreboard of the game.
* */
public class ScoreBoard {

    @FXML
    private TextField name1,name2,name3,name4,name5,name6,name7,name8,name9,name10, score1, score2, score3, score4, score5, score6, score7, score8, score9, score10;
    private List<TextField> nameTextFieldList, scoreTextFieldList;

    private List<String> nameList=new ArrayList<>(),scoreList=new ArrayList<>();

    /**
    * push textfield into the TextField Lists,which store the name TextField and rank TextField of the scoreboard.fxml.
    *
    * push textfield into TextField Lists sequentially,so that we can get specific TextField according to the list index.
    * */
    public void initTextFieldList(){
        nameTextFieldList = Arrays.asList(name1,name2,name3,name4,name5,name6,name7,name8,name9,name10);
        scoreTextFieldList =Arrays.asList(score1, score2, score3, score4, score5, score6, score7, score8, score9, score10);
    }
    /**
     * add name and score into corresponding list.
     *
     * this method judge the score is higher than list's scores or not,and push higher one into new list.
     * lastly,it replace the old list with the new one,and save list in the file.
     * @param name the name of player.
     * @param score the score of player.
     * */
    public void addNameAndScore(String name, String score){
        List<String> tempNameList=new ArrayList<>(),tempScoreList=new ArrayList<>();
        boolean flag=true;
        for(int i=0;i<nameList.size()&&tempScoreList.size()<10;i++){
            if(Integer.parseInt(scoreList.get(i))<=Integer.parseInt(score)&&flag){
                tempNameList.add(name);
                tempScoreList.add(score);
                flag=false;//add new data only once
            }
            tempNameList.add(nameList.get(i));
            tempScoreList.add(scoreList.get(i));
        }
        if(nameList.size()<10&&flag){//
            tempNameList.add(name);
            tempScoreList.add(score);
        }

        nameList.clear();scoreList.clear();
        nameList.addAll(tempNameList);scoreList.addAll(tempScoreList);
        writeFile();
    }

    /**
    * read persistent data form the file,and save the in list temporarily.
    *
    * Note:origin list is cleared before read data form file.
    * */
    public void readFile(){
        nameList.clear();scoreList.clear();
        BufferedReader bufferedReader;
        File file=new File("text/ranklist.txt");
        try {
            bufferedReader=new BufferedReader(new FileReader(file));
            String temp;
            while ((temp=bufferedReader.readLine())!=null){
                String[] element=temp.split(" ");
                nameList.add(element[0]);
                scoreList.add(element[1]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public List<String> testReadScoreBoardFile(){
        readFile();
        return scoreList;
    }
    /**
    * save list data in the file.
    * */
    private void writeFile(){
        BufferedWriter bufferedWriter;
        File file=new File("text/ranklist.txt");
        try {
            bufferedWriter=new BufferedWriter(new FileWriter(file));
            for(int i=0;i<nameList.size();i++){
                bufferedWriter.write(nameList.get(i)+" "+scoreList.get(i));
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    /**
    * display the name and score in the TextField.
    * */
    public void setTextField(){
        for(int i=0;i<nameList.size();i++){
            nameTextFieldList.get(i).setText(nameList.get(i));
            scoreTextFieldList.get(i).setText(scoreList.get(i));
        }
    }

}
