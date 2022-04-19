package sample;


import java.util.*;
import java.util.stream.Collectors;

/**
 * init a numerical map,which represents graphics with numbers
 *
 * this class use backtracking algorithm to get a legal graphics,and save some significant data.
 * {@code shapeCount} save number of each shape.
 * {@code adjMap} save the adjacency relation of each point.
 */
public class InitNumericalMap {
    List<List<Boolean>> adjMap=new ArrayList<>();
    private List<Integer> resultList=new ArrayList<>();

    private boolean hasResultList =false;
    private Random random=new Random();

    public static int nodeNum=16,shapeNum=5;

    private int [] shape=new int[nodeNum];
    int [] shapeCount=new int[]{0,0,0,0,0};

    /**
     * init AdjMap.
     */
    public void initAdjMap(){
        for(int i=0;i<nodeNum;i++){
            List<Boolean> temp=new ArrayList<>();
            for(int j=0;j<nodeNum;j++){
                //horizontally adjacent
                if(i-j==1&&i%4!=0){//not the leftmost row
                    temp.add(true);
                }else if(i-j==-1&&(i%4)!=3){//not the rightmost row
                    temp.add(true);
                }else if(i - j == 4 || i - j == -4){//vertically adjacent
                    temp.add(true);
                }else{
                    temp.add(false);
                }
            }
            adjMap.add(temp);
        }

    }

    /**
     * {@code pos} can place a specific shape or not
     * @param pos is the position of point you want to place a specific shape
     * @return {@code false} if there are same shape nearby.{@code true} if there are no ame shape nearby
     */
    private boolean canPlaced(int pos){
        for(int i=0;i<pos;i++){
            if(adjMap.get(pos).get(i)){//i have adjacency relation with pos points
                if(shape[i]==shape[pos]){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * used in test class to test private method canPlaced
     * @param pos the new shape's position
     * @param value the new shape
     * @return {@code false} if there are same shape nearby.{@code true} if there are no ame shape nearby
     */
    public boolean canPlace(int pos,int value){
        shape[pos]=value;
        return canPlaced(pos);
    }

    /**
     * the getter of resultList
     * @return address of the resultList
     */
    public List<Integer> getResultList(){
        return resultList;
    }

    /**
     * is shape legal or not
     * @return {@code true} if each element of shape is different from nearby shapes,{@code false} if not.
     */
    private boolean isLegal(){
        for(int i=0;i<nodeNum;i++){
            for(int j=0;j<nodeNum;j++){
                if(adjMap.get(j).get(i)){
                    if(shape[i]==shape[j]){
                        return false;
                    }
                }
            }
        }
        return  true;
    }
    /**
     * used in test class to test private method isLegal
     * @param shape the specific shape array
     * @return {@code true} if each element of shape is different from nearby shapes,{@code false} if not.
     */
    public boolean isLegal(int[] shape){
        this.shape=shape;
        return isLegal();
    }

    /**
     * 23344 rule is followed or not.
     * @return {@code true} if the count of five shapes follow 23344,{@code false} if not
     */
    private boolean isLegalCount(){
        shapeCount=new int[]{0,0,0,0,0};
        int time2=0,time3=0,time4=0;
        for(Integer element:resultList){
            shapeCount[element]++;
        }
        for(int i=0;i<shapeNum;i++){
            if(shapeCount[i]==2){
                time2++;
            }else if(shapeCount[i]==3){
                time3++;
            }else if(shapeCount[i]==4){
                time4++;
            }
        }
        if(time2==1&&time3==2&&time4==2){
            return true;
        }
        return false;
    }

    /**
     * used in test class to test private method isLegalCount
     * @param shape the specific shape array
     * @return {@code true} if the count of five shapes follow 23344,{@code false} if not
     */
    public boolean isLegalCount(int[] shape){
        for(int element:shape){
            resultList.add(element);
        }
        return isLegalCount();
    }

    /**
     * this method create a random result list by backtracking
     * @param t the position to add a random number to {@code shape} array
     */
    public void backTrace(int t){
        if(!hasResultList){//if result list has created, then all other loop is useless and should pass
            if(t==nodeNum-1){
                if(resultList.isEmpty()){
                    resultList=Arrays.stream(shape).boxed().collect(Collectors.toList());
                    if(isLegalCount()&&isLegal()){
                        hasResultList =true;
                    }else{
                        resultList.clear();
                    }
                }
            }else{
                int tempRandom=random.nextInt(5);
                shape[t]=tempRandom;
                while (!canPlaced(t)){
                    tempRandom=random.nextInt(5);
                    shape[t]=tempRandom;
                }
                backTrace(t+1);
            }
        }
    }

    /**
     * set a value in the private member array shape
     * @param index the index to set
     * @param value the value to set
     */
    public void setShapeArray(int index,int value){
        shape[index]=value;
    }

    /**
     * getter of hasResultList
     * @return hasResultList
     */
    public boolean isHasResultList() {
        return hasResultList;
    }

    public void initSpecificMap(){
        resultList = Arrays.asList(0,1,0,3,4,2,4,1,0,3,0,2,4,1,4,1);

        hasResultList = true;

        shapeCount[0] = 4;
        shapeCount[1] = 4;
        shapeCount[2] = 2;
        shapeCount[3] = 2;
        shapeCount[4] = 4;
    }
}
