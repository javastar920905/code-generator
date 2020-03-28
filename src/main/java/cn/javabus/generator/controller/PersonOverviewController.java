package cn.javabus.generator.controller;

import cn.javabus.generator.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * 所有fxml文件需要访问的属性和方法必须加上 @FXML 注解.实际上,只有在私有的情况下才需要
 *
 * @author ouzhx on 2019/5/22.
 */
public class PersonOverviewController implements Initializable {
    private Stage primaryStage;
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;

    /**
     * JavaFX的view classes需要在人员列表发生任何改变时都被通知..为了达到这个目的,JavaFX引入了一些新的集合类.
     * 使用 ObservableList The data as an observable list of Persons.
     */
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    public PersonOverviewController() {
        // Add some sample data
        personData.add(new Person("Hans", "Muster"));
        personData.add(new Person("Ruth", "Mueller"));
        personData.add(new Person("Heinz", "Kurz"));
        personData.add(new Person("Cornelia", "Meier"));
        personData.add(new Person("Werner", "Meyer"));
        personData.add(new Person("Lydia", "Kunz"));
        personData.add(new Person("Anna", "Best"));
        personData.add(new Person("Stefan", "Meier"));
        personData.add(new Person("Martin", "Mueller"));
//        personTable=new TableView<>();

    }


    /**
     * initialize() 方法在fxml文件完成载入时被自动调用. 那时, 所有的FXML属性都应已被初始化
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        //设置数据,实时反馈到 javafx
        personTable.setItems(personData);

        //使用showPersonDetails(null)，我们重设个人详情。
        showPersonDetails(null);
        // 监听personTable列表选中事件
        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }


    /**
     * 与用户交互
     * https://code.makery.ch/zh-cn/library/javafx-tutorial/part3/
     *
     * @param person
     */
    public void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            firstNameLabel.setText(person.getFirstName());
//            lastNameLabel.setText(person.getLastName());
//            streetLabel.setText(person.getStreet());
//            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());

            //  We need a way to convert the birthday into a String!
            // birthdayLabel.setText(...);
        } else {
            // Person is null, remove all the text.
            firstNameLabel.setText("");
//            lastNameLabel.setText("");
//            streetLabel.setText("");
//            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public TableView<Person> getPersonTable() {
        return personTable;
    }

    public void setPersonTable(TableView<Person> personTable) {
        this.personTable = personTable;
    }

    public TableColumn<Person, String> getFirstNameColumn() {
        return firstNameColumn;
    }

    public void setFirstNameColumn(TableColumn<Person, String> firstNameColumn) {
        this.firstNameColumn = firstNameColumn;
    }

    public TableColumn<Person, String> getLastNameColumn() {
        return lastNameColumn;
    }

    public void setLastNameColumn(TableColumn<Person, String> lastNameColumn) {
        this.lastNameColumn = lastNameColumn;
    }

    public Label getFirstNameLabel() {
        return firstNameLabel;
    }

    public void setFirstNameLabel(Label firstNameLabel) {
        this.firstNameLabel = firstNameLabel;
    }

    public Label getLastNameLabel() {
        return lastNameLabel;
    }

    public void setLastNameLabel(Label lastNameLabel) {
        this.lastNameLabel = lastNameLabel;
    }

    public Label getStreetLabel() {
        return streetLabel;
    }

    public void setStreetLabel(Label streetLabel) {
        this.streetLabel = streetLabel;
    }

    public Label getPostalCodeLabel() {
        return postalCodeLabel;
    }

    public void setPostalCodeLabel(Label postalCodeLabel) {
        this.postalCodeLabel = postalCodeLabel;
    }

    public Label getCityLabel() {
        return cityLabel;
    }

    public void setCityLabel(Label cityLabel) {
        this.cityLabel = cityLabel;
    }

    public Label getBirthdayLabel() {
        return birthdayLabel;
    }

    public void setBirthdayLabel(Label birthdayLabel) {
        this.birthdayLabel = birthdayLabel;
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public void setPersonData(ObservableList<Person> personData) {
        this.personData = personData;
    }
}
