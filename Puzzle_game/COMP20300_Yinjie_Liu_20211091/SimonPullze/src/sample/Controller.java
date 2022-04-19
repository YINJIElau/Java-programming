package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * controller of the layout.fxml
 */
public class Controller implements Initializable {

    @FXML
    private ImageView grid11,grid12,grid13,grid14,grid21,grid22,grid23,grid24,grid31,grid32,grid33,grid34,grid41,grid42,grid43, grid44;
    @FXML
    private Button circleButton,rectangleButton,triangleButton,pentagonButton,hexagonButton,checkRL,checkHistory,revokeButton,restart;
    @FXML
    private TextField toast,circleLeft,rectangleLeft,triangleLeft,pentagonLeft,hexagonLeft,circleToTal,rectangleToTal,triangleToTal,pentagonToTal,hexagonToTal,totalHistory,historyIndex;

    private List<ImageView> imageViewList;

    private List<Integer> resultList;//the reflect of the resultList in the InitNumericalMap Class

    private List<Boolean> isPlacedList; //the position is placed a new shape or not
    private InitNumericalMap initNumericalMap =new InitNumericalMap();

    //when you place a new shape, the number of each shape will change, this array save new shape count dynamically
    int [] dynamicShapeCount;
    private int totalHistoryNum;

    private Integer selectedShape;//shape wants to place

    private ScoreBoard scoreBoard;
    private String name,score;

    private Date date;
    private SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
    private String startTime;

    //save data of last step;the element of List is index,selectedPiece,originShape(resultList.get(index))
    private List<Integer> lastStep;
    //a stack to save all lastStep Lists
    private LinkedList stepsList=new LinkedList();

    /**
     * convert numerical map to graphical interface
     */
    public void initGraphicalMap(){
        initNumericalMap.initAdjMap();
        while(initNumericalMap.getResultList().isEmpty()){
            initNumericalMap.backTrace(0);
        }
        //initNumericalMap.initSpecificMap();

        resultList= initNumericalMap.getResultList();
        dynamicShapeCount =new int[]{0,0,0,0,0};
        selectedShape =-1;

        setStartTime();
        setHistoryTextFiled();

        imageViewList=Arrays.asList(grid11, grid12, grid13, grid14, grid21, grid22, grid23, grid24, grid31, grid32, grid33, grid34, grid41, grid42, grid43, grid44);
        isPlacedList =Arrays.asList(false,false,false,false,false,false,false,false,false,false,false,false,false,false,false,false);
        int i=0;
        for(Integer element:resultList){
            setUnPlacedShapePicture(i,element);
            i++;
        }

        System.arraycopy(initNumericalMap.shapeCount,0, dynamicShapeCount,0, initNumericalMap.shapeCount.length);
        setShapeLeftNum();
        setShapeTotalNum();

        setActionListenerForImageViews();
    }

    /**
     * set start time of game
     */
    private void setStartTime(){
        date=new Date();
        startTime=format.format(date);
    }

    /**
     * used in test class to test private method setStartTime
     */
    public String testStartTime(){
        setStartTime();
        return startTime;
    }

    /**
     * read history file and set historyTextFile to display total number of history
     */
    private void setHistoryTextFiled(){
        totalHistoryNum=0;
        readHistoryRecord();
        totalHistory.setText("There are "+totalHistoryNum+" history records");
    }

    /**
     * display number of unplaced shape in textField
     */
    private void setShapeLeftNum(){
        circleLeft.setText(String.valueOf(initNumericalMap.shapeCount[0]));
        hexagonLeft.setText(String.valueOf(initNumericalMap.shapeCount[1]));
        pentagonLeft.setText(String.valueOf(initNumericalMap.shapeCount[2]));
        rectangleLeft.setText(String.valueOf(initNumericalMap.shapeCount[3]));
        triangleLeft.setText(String.valueOf(initNumericalMap.shapeCount[4]));
    }

    /**
     * display number of all visible shape in textField
     */
    private void setShapeTotalNum(){
        circleToTal.setText(String.valueOf(dynamicShapeCount[0]));
        hexagonToTal.setText(String.valueOf(dynamicShapeCount[1]));
        pentagonToTal.setText(String.valueOf(dynamicShapeCount[2]));
        rectangleToTal.setText(String.valueOf(dynamicShapeCount[3]));
        triangleToTal.setText(String.valueOf(dynamicShapeCount[4]));
    }

    /**
     * set placed {@code shape} picture in {@code index} imageview position
     * @param index the position of imageview to display picture
     * @param shape the shape to display
     */
    private void setPlacedShapePicture(Integer index, Integer shape){
        Image circleSelected=new Image("file:/F:/SimonPullze/picture/circleSelected.png")
                ,hexagonSelected=new Image("file:/F:/SimonPullze/picture/hexagonSelected.png")
                ,pentagonSelected=new Image("file:/F:/SimonPullze/picture/pentagonSelected.png")
                ,rectangleSelected=new Image("file:/F:/SimonPullze/picture/rectangleSelected.png")
                ,triangleSelected=new Image("file:/F:/SimonPullze/picture/triangleSelected.png");
        switch (shape){
            case 0:
                imageViewList.get(index).setImage(circleSelected);
                break;
            case 1:
                imageViewList.get(index).setImage(hexagonSelected);
                break;
            case 2:
                imageViewList.get(index).setImage(pentagonSelected);
                break;
            case 3:
                imageViewList.get(index).setImage(rectangleSelected);
                break;
            default:
                imageViewList.get(index).setImage(triangleSelected);
        }

    }

    /**
     * set unplaced {@code shape} picture in {@code index} imageview position
     * @param index the position of imageview to display picture
     * @param shape the shape to display
     */
    private void setUnPlacedShapePicture(Integer index, Integer shape){
        Image circle=new Image("file:/F:/SimonPullze/picture/circle.png")
                ,hexagon=new Image("file:/F:/SimonPullze/picture/hexagon.png")
                ,pentagon=new Image("file:/F:/SimonPullze/picture/pentagon.png")
                ,rectangle=new Image("file:/F:/SimonPullze/picture/rectangle.png")
                ,triangle=new Image("file:/F:/SimonPullze/picture/triangle.png");
        switch (shape){
            case 0:
                imageViewList.get(index).setImage(circle);
                break;
            case 1:
                imageViewList.get(index).setImage(hexagon);
                break;
            case 2:
                imageViewList.get(index).setImage(pentagon);
                break;
            case 3:
                imageViewList.get(index).setImage(rectangle);
                break;
            default:
                imageViewList.get(index).setImage(triangle);
        }
    }
    /**
     * set click action listener for each imageview in imageviewList
     */
    private void setActionListenerForImageViews(){
        for(ImageView imageView:imageViewList){
            imageView.setOnMouseClicked(v->{
                placePiece(imageViewList.indexOf(imageView));
            });
        }
    }

    /**
     * setter of {@code name}
     * @param name the name of player
     */
    public void setName(String name){
        this.name=name;
    }

    /**
     * return to the result of the previous operation
     */
    private void revoke(){
        if(stepsList.isEmpty()){
            toast.setText("You have do nothing!");
        }else{
            lastStep= (List<Integer>) stepsList.removeLast();
            toast.setText("Revoke Successfully");
            isPlacedList.set(lastStep.get(0),false);
            initNumericalMap.shapeCount[lastStep.get(1)]++;
            dynamicShapeCount[lastStep.get(1)]--;
            dynamicShapeCount[lastStep.get(2)]++;
            resultList.set(lastStep.get(0),lastStep.get(2));

            setUnPlacedShapePicture(lastStep.get(0),lastStep.get(2));
            setShapeLeftNum();
            setShapeTotalNum();

            selectedShape =-1;
        }
    }

    /**
     * place a new shape in the {@code index}.
     *
     * firstly,this method will judge whether you have selected a shape to place.
     * then,whether the origin shape is same with new shape .
     * and,whether placing the new shape against with 23344 Rule or Adjacent Rule.
     * if the placement is legal,new shape will be placed, and shapeLeft TextField and shapeTotal TextField will be renew
     * everytime this method is called , system will judge whether the win/lose condition is met.
     * @param index the position of imageview which you want to place a new shape
     */
    private void placePiece(Integer index){
        if(selectedShape !=-1){
            if(isSameShape(index)){
                toast.setText("You can not place here,because they are the same shape");
            }else{
                if(noSameShapeAdj(index)){
                    if(notChangeRatio(resultList.get(index))){
                        toast.setText("Place Successfully");

                        isPlacedList.set(index, true);
                        initNumericalMap.shapeCount[selectedShape]--;
                        dynamicShapeCount[selectedShape]++;
                        dynamicShapeCount[resultList.get(index)]--;

                        lastStep=new ArrayList<>();
                        lastStep.add(index);
                        lastStep.add(selectedShape);
                        lastStep.add(resultList.get(index));
                        stepsList.add(lastStep);


                        resultList.set(index, selectedShape);
                        setPlacedShapePicture(index, selectedShape);
                        setShapeLeftNum();
                        setShapeTotalNum();

                        selectedShape =-1;

                        if(isLose()){
                            Alert alert=new Alert(Alert.AlertType.INFORMATION,"YOU FAIL!");
                            alert.show();
                            writeHistoryFile();
                            setScore(false);
                            scoreBoard.addNameAndScore(name,score);
                        }
                        if(isWin()){
                            Alert alert=new Alert(Alert.AlertType.INFORMATION,"YOU SUCCESS!");
                            alert.show();
                            writeHistoryFile();
                            setScore(true);
                            scoreBoard.addNameAndScore(name,score);
                        }
                    }else {
                        toast.setText("Against With 23344 Rule!");
                        }
                }else{
                    toast.setText("Against With Same Shape Not Adjacent Rule!");
                }
            }
        }else {
            toast.setText("You have not selected the shape to place");
        }
    }


    /**
     * everytime game is over(win/lose),the record of this game will be write in result.txt
     */
    private void writeHistoryFile(){
        BufferedWriter bufferedWriter;
        File file=new File("text/result.txt");
        try{
            if(!file.exists()){
                file.createNewFile();
            }
            bufferedWriter=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file,true)));
            bufferedWriter.newLine();
            bufferedWriter.write(resultList.toString().substring(1,resultList.toString().length()-1));
            bufferedWriter.newLine();
            bufferedWriter.write(isPlacedList.toString().substring(1, isPlacedList.toString().length()-1));
            bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     *read total number of history records
     */
    private void readHistoryRecord(){
        BufferedReader bufferedReader;
        File file=new File("text/result.txt");
        try {
            bufferedReader=new BufferedReader(new FileReader(file));
            while (bufferedReader.readLine()!=null){
                totalHistoryNum++;
            }
            totalHistoryNum/=2;//One message occupies two lines
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * used to test readHistoryRecord method
     * @return totalHistoryNum
     */
    public int testReadHistoryRecord(){
        readHistoryRecord();
        return totalHistoryNum;
    }

    /**
     * read the specific history record
     *
     * owing to the resultList and isPlacedList is init in the {@code initGraphicalMap}, they will be cleared before read record!
     * if the index is within bound, this method calls {@code loadHistoryRecord} to load record in the interface
     * @param index the specific records you want to obtain.NOTE:index starts at 1 instead of 0!
     */
    private void readHistoryRecord(int index){
        BufferedReader bufferedReader;
        File file=new File("text/result.txt");
        try {
            bufferedReader=new BufferedReader(new FileReader(file));
            int i=0;
            String temp;
            while ((temp=bufferedReader.readLine())!=null){
                i++;
                if(i==index*2-1){
                    String [] tempList=temp.split(", ");
                    resultList.clear();
                    for(String element:tempList){
                        resultList.add(Integer.valueOf(element));
                    }
                }
                if(i==index*2){
                    String [] tempList=temp.split(", ");
                    isPlacedList =new ArrayList<>();
                    for(String element:tempList){
                        isPlacedList.add(Boolean.valueOf(element));
                    }
                }
            }
            if(i>=index*2){
                loadHistoryRecord();
            }else{
                toast.setText("Beyond records bound！");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * load the history record in the interface
     */
    private void loadHistoryRecord(){
        for(int j = 0; j< initNumericalMap.shapeNum; j++){
            initNumericalMap.shapeCount[j]=0;
            dynamicShapeCount[j]=0;
        }
        int i=0;
        for(Boolean isPlacedPiece: isPlacedList){
            if(isPlacedPiece){
                setPlacedShapePicture(i,resultList.get(i));
            }else{
                setUnPlacedShapePicture(i,resultList.get(i));
                initNumericalMap.shapeCount[resultList.get(i)]++;
            }
            dynamicShapeCount[resultList.get(i)]++;
            i++;
        }
        setShapeLeftNum();
        setShapeTotalNum();
    }

    /**
     * calculate the score of game according to the result of game.
     *
     * the calculation formula is PlacedPiece*100 - SecondsUsed (+ 600)(if win).
     * @param isWin the game is win or not
     */
    private void setScore(boolean isWin){
        int seconds;
        date=new Date();
        String endTime = format.format(date);

        String []startTimeList=startTime.split(":"),endTimeList= endTime.split(":");

        seconds=(Integer.parseInt(endTimeList[0])-Integer.parseInt(startTimeList[0]))*3600+
                (Integer.parseInt(endTimeList[1])-Integer.parseInt(startTimeList[1]))*60+
                Integer.parseInt(endTimeList[2])-Integer.parseInt(startTimeList[2]);

        if(isWin){
            score= String.valueOf(Collections.frequency(isPlacedList,true)*100-seconds+600);
        }else {
            score= String.valueOf(Collections.frequency(isPlacedList,true)*100-seconds);
        }
    }
    public String testSetScore(boolean isWin,String startTime,List<Boolean> isPlacedList){
        this.startTime= startTime;
        this.isPlacedList=isPlacedList;
        setScore(isWin);
        return score;
    }


    /**
     * whether the shape of {@code pos} observe the Same Shape Not Adjacent or not
     * @param pos the position of imageview to place new shape
     * @return {@code false} if against Same Shape Not Adjacent, {@code true} if observe the rule
     */
    private boolean noSameShapeAdj(Integer pos){
        for(int i = 0; i< InitNumericalMap.nodeNum; i++){
            if(initNumericalMap.adjMap.get(pos).get(i)){//i与pos有相邻关系
                if(resultList.get(i).equals(selectedShape)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * whether placing new shape against 23344 Rule
     * @param shape the new shape
     * @return {@code false} if against 23344 Rule,{@code true} if observe the rule
     */
    private boolean notChangeRatio(Integer shape){
        if(dynamicShapeCount[selectedShape]- dynamicShapeCount[shape]!=-1){
            return false;
        }
        return true;
    }
    public boolean testNotChangeRatio(int[] dynamicShapeCount,int selectedShape,int shape){
        this.dynamicShapeCount=dynamicShapeCount;
        this.selectedShape=selectedShape;
        return notChangeRatio(shape);
    }

    /**
     * whether the origin shape of {@code index} is same with {@code selectedShape}
     * @param index the position of imageview
     * @return {@code true} if is same shape, {@code false} if is not
     */
    private boolean isSameShape(Integer index){
        if(selectedShape ==resultList.get(index)){
            return true;
        }
        return false;
    }

    /**
     * used in test class to test isSameShape method
     * @param selectedShape the selectedShape
     * @param index the index
     * @param resultList the specific resultList
     * @return
     */
    public boolean testIsSameShape(Integer selectedShape,Integer index,List<Integer> resultList){
        this.resultList=resultList;
        this.selectedShape=selectedShape;
        return isSameShape(index);
    }

    /**
     * whether the game is lose
     *
     * firstly,this method find every not placed position, and traverse all remaining shapes.
     * if can place arbitrary remaining shapes in arbitrary not placed position, the game is not lose
     * if the game is win, the game is not lose
     * otherwise, the game is lose
     * @return {@code true} if the game is lose,{@code false} if is not
     */
    private boolean isLose(){
        int j=0;
        for(Boolean element: isPlacedList){
            if(!element){//every not placed position
                for(int i = 0; i< InitNumericalMap.shapeNum; i++){
                    if(initNumericalMap.shapeCount[i]!=0) {//traverse all remaining shapes
                        selectedShape =i;
                        if(!isSameShape(j)&& noSameShapeAdj(j)&&notChangeRatio(resultList.get(j))){
                            selectedShape =-1;
                            return false;
                        }
                    }
                }
            }
            j++;
        }
        if(isWin()){
            return false;
        }
        selectedShape =-1;
        return true;
    }

    /**
     * used in test class to test isLost method
     * @param isPlacedList a specific isPlacedList
     * @param resultList specific resultList
     * @param shapeCount total shape count
     * @param dynamicShapeCount remain shape count
     * @return {@code true} if the game is lose,{@code false} if is not
     */
    public boolean testIsLose(List<Boolean> isPlacedList,List<Integer> resultList,int[] shapeCount,int[] dynamicShapeCount){
        initNumericalMap.initAdjMap();
        this.isPlacedList=isPlacedList;
        this.resultList=resultList;
        this.initNumericalMap.shapeCount=shapeCount;
        this.dynamicShapeCount=dynamicShapeCount;
        return isLose();
    }

    /**
     * whether the game is win or not
     * @return {@code true} if the remaining shape is 0, {@code false} if not
     */
    public boolean isWin(){
        for(int i = 0; i< InitNumericalMap.shapeNum; i++){
            if(initNumericalMap.shapeCount[i]!=0) {
                return false;
            }
        }
        return true;
    }

    public boolean testIsWin(int[] shapeCount){
        this.initNumericalMap.shapeCount=shapeCount;
        return isWin();
    }

    /**
     *create button click listener in initialize method.
     * create scoreboard scene
     */
    @Override
    public void initialize(URL location, ResourceBundle resources){
        circleButton.setOnAction(e->{
            if(initNumericalMap.shapeCount[0]>0){
                selectedShape =0;
                toast.setText("You choose circle");
            }else{
                toast.setText("You have run out of this shape");
            }
        });
        hexagonButton.setOnAction(e->{
            if(initNumericalMap.shapeCount[1]>0){
                selectedShape =1;
                toast.setText("You choose hexagon");
            }else{
                toast.setText("You have run out of this shape");
            }
        });
        pentagonButton.setOnAction(v->{
            if(initNumericalMap.shapeCount[2]>0){
                selectedShape =2;
                toast.setText("You choose pentagon");
            }else{
                toast.setText("You have run out of this shape");
            }
        });
        rectangleButton.setOnAction(v->{
            if(initNumericalMap.shapeCount[3]>0){
                selectedShape =3;
                toast.setText("You choose rectangle");
            }else{
                toast.setText("You have run out of this shape");
            }
        });
        triangleButton.setOnAction(v->{
            if(initNumericalMap.shapeCount[4]>0){
                selectedShape =4;
                toast.setText("You choose triangle");
            }else{
                toast.setText("You have run out of this shape");
            }
        });

        checkHistory.setOnAction(v->{
            if(!historyIndex.getText().isEmpty()){
                readHistoryRecord(Integer.parseInt(historyIndex.getText()));
            }else{
                toast.setText("Input the record index you want to check");
            }
        });

        revokeButton.setOnAction(v->{
            revoke();
        });

        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("scoreboard.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root= null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        scoreBoard =fxmlLoader.getController();
        Scene scene=new Scene(root);
        Stage stage=new Stage();
        stage.setScene(scene);
        checkRL.setOnAction(v->{
            stage.show();
            scoreBoard.initTextFieldList();

            scoreBoard.readFile();
            scoreBoard.setTextField();
        });

        restart.setOnAction(v->{
            initNumericalMap =new InitNumericalMap();
            initGraphicalMap();
        });
    }

}
