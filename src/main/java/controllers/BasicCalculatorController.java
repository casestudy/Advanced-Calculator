package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import utilities.BasicFXUtilities;

import java.net.URL;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class BasicCalculatorController implements Initializable{

    private Stage primaryStage;

    /*Variable declaration*/

    //This list will help us to keep track of the current number we are entering and want to use in calculating
    private LinkedList<String> buffer = new LinkedList<>() ;

    //Will be used to store the current operation we are performing
    private String operation ;

    /*This string will be used to keep track of the last buffer*/
    private String lastBuffer ;

    //Will be used to the result from the previous operation
    private String currentResult = "" ;

    @FXML
    private Label memoryStatus;

    /*This list will help us keep track of all the operations we are about to perform*/
    private LinkedList <String> operationLists = new LinkedList<>() ;



    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  <tt>null</tt> if the location is not known.
     * @param resources The resources used to localize the root object, or <tt>null</tt> if
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        primaryStage.setTitle("Calculator - Basic");
        primaryStage.setResizable(false);
    }

    @Autowired
    public void setPrimaryStage (Stage primaryStage) {
        this.primaryStage = primaryStage ;
    }

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="answerArea"
    private TextField answerArea; // Value injected by FXMLLoader

    @FXML // fx:id="operationsArea"
    private TextField operationsArea; // Value injected by FXMLLoader

    @FXML
    void addToMemory(ActionEvent event) {


    }

    @FXML
    void appendNegative(ActionEvent event) {
        if (BasicFXUtilities.getCurrentResult() != null){
            if (!BasicFXUtilities.getCurrentResult().isEmpty()) {
                if (buffer.size() > 0) {
                    answerArea.insertText(0,"-");
                    buffer.addFirst(((Button) event.getSource()).getText());
                } else {
                    System.out.println("Buffer is zero...====");
                    System.out.println("Display the operations list: " + operationLists);

                    answerArea.insertText(0,"-");
                    buffer.addFirst(((Button) event.getSource()).getText());
                    buffer.add(BasicFXUtilities.getCurrentResult());

                    System.out.println("Now the current result is: " + BasicFXUtilities.getCurrentResult());
                    System.out.println("The buffer now is: " + buffer);
                }
            } else {
                if (buffer.size() > 0) {
                    answerArea.insertText(0,"-");
                    buffer.addFirst(((Button) event.getSource()).getText());
                } else {
                    System.out.println("Current result is zero===========");
                    System.out.println("Display current operations list again: " + operationLists);
                }
            }
        } else {
            if (buffer.size() > 0){
                if (Objects.equals(buffer.getFirst(), "±")){
                    System.out.println("negative already exist");
                } else {
                    answerArea.insertText(0,"-");
                    buffer.addFirst(((Button) event.getSource()).getText());
                }
            } else {
                System.out.println("It is null and buffer is zero");
            }
        }
    }

    @FXML
    void calculate(ActionEvent event) {
        displayResultsFromEqualButton();
    }

    @FXML
    void clearAnswerAndOperationArea(ActionEvent event) {
        buffer.clear();
        answerArea.clear();
        operationsArea.clear();
        operationLists.clear();
        BasicFXUtilities.setCurrentResult("");
        BasicFXUtilities.setLastBuffer("");
        answerArea.appendText("0");
    }

    @FXML
    void clearAnswerArea(ActionEvent event) {
        buffer.clear();
        answerArea.clear();
        answerArea.appendText("0");
    }

    @FXML
    void clearMemory(ActionEvent event) {
        BasicFXUtilities.setMemoryBuffer("0");
        memoryStatus.setText("");
    }

    @FXML
    void performAddition(ActionEvent event) {
        displayResults(event);
    }

    @FXML
    void performBackSpace(ActionEvent event) {
        try {
            buffer.removeLast() ;
            answerArea.clear();

            for (String bufferList : buffer) {
                answerArea.appendText(bufferList);
            }
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }

        if (buffer.size() == 0) {
            answerArea.clear();
            answerArea.appendText("0");
        }
    }

    @FXML
    void performDivision(ActionEvent event) {
        displayResults(event);
    }

    @FXML
    void performMultiplication(ActionEvent event) {
        displayResults(event);
    }

    @FXML
    void performPercentage(ActionEvent event) {

    }

    @FXML
    void performReciprocal(ActionEvent event) {
        
    }

    @FXML
    void performSquareRoot(ActionEvent event) {
        String firstNumber = "" , secondNumber = "" ;
        /*We want to perform the squared root of a number
        * It can either be on a current result or on a number we just entered.*/

        if (BasicFXUtilities.getCurrentResult() != null){
            if (!BasicFXUtilities.getCurrentResult().isEmpty()) {
            /*Current result not empty means that we are either performing the operation with the current result
            * or with a buffer. In this case, we must check the operations list
            * We  could have something like this: n + sqrt(m)*/

                if (operationLists.size() > 0){
                /*We perform the last operation between the current result and the squared root of the buffer*/

                    if (buffer.size() > 0) {
                        System.out.println("check here =========here toooo");
                        operation = operationLists.getLast() ;

                        for (String item : buffer) {
                            if (Objects.equals(item, "±"))
                                item = "-" ;
                            secondNumber += item;
                        }

                        operationLists.add(((Button) event.getSource()).getText());
                        operationLists.add(secondNumber);

                        if (Double.parseDouble(secondNumber) > 0) {
                            secondNumber = String.valueOf(Math.sqrt(Double.parseDouble(secondNumber)));

                            double result = BasicFXUtilities.performOperation(BasicFXUtilities.getCurrentResult(), secondNumber, operation) ;

                            answerArea.clear();
                            buffer.clear();
                            answerArea.appendText(String.valueOf(result));


                            lastBuffer = secondNumber;
                            BasicFXUtilities.setCurrentResult(String.valueOf(result));
                            //currentResult = String.valueOf(result);

                            BasicFXUtilities.printOperations(operationLists,operationsArea);
                        } else {
                            BasicFXUtilities.informationAlerts("Warning - Possible illegal operation!" , "WARNING" ,"Operation: sqrt("+BasicFXUtilities.getCurrentResult()+")" , "The Square root of negative numbers does not exist unless in the complex domain. If you really want to get an answer to that operation, then please go to the scientific section.\n\nGo to scientific section now?");
                        }
                    } else {
                        if (!BasicFXUtilities.getCurrentResult().isEmpty()) {
                            if (Double.parseDouble(BasicFXUtilities.getCurrentResult()) > 0) {
                                double result = Math.sqrt(Double.parseDouble(BasicFXUtilities.getCurrentResult())) ;
                                answerArea.clear();
                                buffer.clear();
                                answerArea.appendText(String.valueOf(result));

                                operationLists.add(((Button) event.getSource()).getText());
                                operationLists.add(BasicFXUtilities.getCurrentResult());

                                System.out.println("The current result is now this: " + BasicFXUtilities.getCurrentResult());
                                System.out.println("The current result after computation is now this: " + result);

                                lastBuffer = BasicFXUtilities.getCurrentResult() ;
                                BasicFXUtilities.setCurrentResult(String.valueOf(result));
                                //currentResult = String.valueOf(result);

                                BasicFXUtilities.printOperations(operationLists,operationsArea);
                            } else {
                                BasicFXUtilities.informationAlerts("Warning - Possible illegal operation!" , "WARNING" ,"Operation: sqrt("+BasicFXUtilities.getCurrentResult()+")" , "The Square root of negative numbers does not exist unless in the complex domain. If you really want to get an answer to that operation, then please go to the scientific section.\n\nGo to scientific section now?");
                            }
                        } else {
                            System.out.printf("What do you think caused this? DEBUG");
                        }
                    }
                } else {
                /*Probably we have just the buffer. Can't think of a reason why, but we should just
                * do the sqrt of the current result and display*/

                    if (Double.parseDouble(BasicFXUtilities.getCurrentResult()) > 0) {
                        double result = Math.sqrt(Double.parseDouble(BasicFXUtilities.getCurrentResult())) ;

                        answerArea.clear();
                        buffer.clear();
                        answerArea.appendText(String.valueOf(result));

                        //operation = "√" ;

                        operationLists.add(((Button) event.getSource()).getText()) ;
                        operationLists.add(BasicFXUtilities.getCurrentResult());

                        lastBuffer = BasicFXUtilities.getCurrentResult() ;
                        BasicFXUtilities.setCurrentResult(String.valueOf(result));
                        //currentResult = String.valueOf(result) ;

                        BasicFXUtilities.printOperations(operationLists,operationsArea);
                    } else {
                        BasicFXUtilities.informationAlerts("Warning - Possible illegal operation!" , "WARNING" ,"Operation: sqrt("+BasicFXUtilities.getCurrentResult()+")" , "The Square root of negative numbers does not exist unless in the complex domain. If you really want to get an answer to that operation, then please go to the scientific section.\n\nGo to scientific section now?");
                    }

                }
            } else {
            /*Current result is empty. This may mean that this is the first time we are pressing the sqrt button
            * in this case*/

                System.out.println("Observe what will happen and decide");

                if (buffer.size() > 0) {
                /*Then perform squared root on the buffer*/

                    for (String item : buffer) {
                        if (Objects.equals(item, "±"))
                            item = "-" ;
                        firstNumber += item;
                    }

                    if (Double.parseDouble(firstNumber) > 0) {
                        double result = Math.sqrt(Double.parseDouble(firstNumber)) ;
                        answerArea.clear();
                        buffer.clear();
                        answerArea.appendText(String.valueOf(result));

                        //operation = "√" ;

                        //lastBuffer = firstNumber;
                        BasicFXUtilities.setLastBuffer(firstNumber);
                        BasicFXUtilities.setCurrentResult(String.valueOf(result));
                        //currentResult = String.valueOf(result) ;

                        operationLists.add(((Button) event.getSource()).getText()) ;
                        operationLists.add(firstNumber);

                        BasicFXUtilities.printOperations(operationLists,operationsArea);
                    } else {
                        BasicFXUtilities.informationAlerts("Warning - Possible illegal operation!" , "WARNING" ,"Operation: sqrt("+BasicFXUtilities.getCurrentResult()+")" , "The Square root of negative numbers does not exist unless in the complex domain. If you really want to get an answer to that operation, then please go to the scientific section.\n\nGo to scientific section now?");
                    }
                } else {
                /*Here we check if there is a current result and use*/
                    System.out.println("Maybe you should think of poping up to tell user to add some numbers");
                }
            }
        } else {
            if (buffer.size() > 0) {
                /*There is no current result, then this may mean that we are performing squared root for the first time*/

                for (String item : buffer) {
                    if (Objects.equals(item, "±"))
                        item = "-" ;
                    firstNumber += item ;
                }

                if (Double.parseDouble(firstNumber) > 0) {
                    double result = Math.sqrt(Double.parseDouble(firstNumber));

                    buffer.clear();
                    buffer.clear();
                    answerArea.clear();
                    answerArea.appendText(String.valueOf(result));

                    BasicFXUtilities.setLastBuffer(firstNumber);
                    BasicFXUtilities.setCurrentResult(String.valueOf(result));

                    operationLists.add(((Button) event.getSource()).getText()) ;
                    operationLists.add(firstNumber);

                    BasicFXUtilities.printOperations(operationLists,operationsArea);
                } else {
                    BasicFXUtilities.informationAlerts("Warning - Possible illegal operation!" , "WARNING" ,"Operation: sqrt("+firstNumber+")" , "The Square root of negative numbers does not exist unless in the complex domain. If you really want to get an answer to that operation, then please go to the scientific section.\n\nGo to scientific section now?");
                }
            } else {
                System.out.println("Please DEBUG what may have happened here");
            }
        }
    }

    @FXML
    void performSubtraction(ActionEvent event) {
        displayResults(event);
    }


    @FXML
    void printEight(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printFive(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printFour(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printNine(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printOne(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printPoint(ActionEvent event) {
        if (BasicFXUtilities.getOperation() != null){
            operation = BasicFXUtilities.getOperation();
            BasicFXUtilities.displayPoint(answerArea,buffer,operation,event);
        }

    }

    @FXML
    void printSeven(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printSix(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printThree(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printTwo(ActionEvent event) {
        BasicFXUtilities.displayNumber(answerArea, buffer, event);
    }

    @FXML
    void printZero(ActionEvent event) {
        BasicFXUtilities.displayZero(answerArea,buffer,operationLists,event);
    }

    @FXML
    void recallMemory(ActionEvent event) {
        if (BasicFXUtilities.getMemoryBuffer() != null) {
            answerArea.clear();
            answerArea.setText(BasicFXUtilities.getMemoryBuffer());

            //operationLists.clear();
            buffer.clear();

            System.out.println("The memory buffer is: " + BasicFXUtilities.getMemoryBuffer());
            System.out.println("The current result is: " + BasicFXUtilities.getCurrentResult());

            currentResult = BasicFXUtilities.getCurrentResult() ;

            System.out.println("current result has been set to: " + currentResult);

            buffer.add(BasicFXUtilities.getMemoryBuffer());

            //BasicFXUtilities.setCurrentResult("");
            //BasicFXUtilities.setLastBuffer("");
        } else {
            System.out.println("Memory buffer is false");
        }

    }

    @FXML
    void removeFromMemory(ActionEvent event) {

    }

    @FXML
    void setMemory(ActionEvent event) {
        String temp = "";

        if (BasicFXUtilities.getCurrentResult() != null) {
            if (BasicFXUtilities.getCurrentResult().isEmpty()) {
                if (buffer.size() != 0) {
                    for (String aBuffer : buffer) {
                        temp += aBuffer;
                    }

                    BasicFXUtilities.setMemoryBuffer(temp) ;
                    memoryStatus.setText("M");
                }
            } else {
                BasicFXUtilities.setMemoryBuffer(BasicFXUtilities.getCurrentResult());
                memoryStatus.setText("M");
            }
        } else {
            System.out.println("Current result is null");
        }
    }

    @FXML
        // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert answerArea != null : "fx:id=\"answerArea\" was not injected: check your FXML file 'basic.fxml'.";
        assert operationsArea != null : "fx:id=\"operationsArea\" was not injected: check your FXML file 'basic.fxml'.";

    }

    /*Custom methods*/
    private void displayResults(ActionEvent event){
        if (BasicFXUtilities.getCurrentResult() != null) {
            currentResult = BasicFXUtilities.getCurrentResult();
            lastBuffer = BasicFXUtilities.getLastBuffer();
            operation = BasicFXUtilities.getOperation() ;

            BasicFXUtilities.performOperation(operationLists,buffer,operationsArea,answerArea,currentResult,operation,lastBuffer,event);
        } else {
            BasicFXUtilities.performOperation(operationLists,buffer,operationsArea,answerArea,currentResult,operation,lastBuffer,event);
        }
    }

    private void displayResultsFromEqualButton () {
        if (BasicFXUtilities.getCurrentResult() != null){
            currentResult = BasicFXUtilities.getCurrentResult();
            lastBuffer = BasicFXUtilities.getLastBuffer();
            operation = BasicFXUtilities.getOperation() ;

            BasicFXUtilities.performOperationUsingEqualButton(operationLists,buffer,answerArea,operationsArea, currentResult, operation, lastBuffer);
        } else {
            BasicFXUtilities.setCurrentResult("") ;
            BasicFXUtilities.performOperationUsingEqualButton(operationLists,buffer,answerArea,operationsArea, currentResult, operation, lastBuffer);
        }
    }
}
