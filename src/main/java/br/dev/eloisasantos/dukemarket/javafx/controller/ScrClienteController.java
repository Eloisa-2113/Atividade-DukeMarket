package br.dev.eloisasantos.dukemarket.javafx.controller;

import br.dev.eloisasantos.dukemarket.javafx.DAO.ClienteDAO;
import br.dev.eloisasantos.dukemarket.javafx.model.Cliente;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author sesi2dia
 */
public class ScrClienteController implements Initializable {

    @FXML
    TableView<Cliente> tblCliente;

    @FXML
    TableColumn<Cliente, Integer> tcoId;

    @FXML
    TableColumn<Cliente, String> tcoNome;

    @FXML
    TableColumn<Cliente, String> tcoEndereco;

    @FXML
    TableColumn<Cliente, String> tcoCidade;

    @FXML
    TableColumn<Cliente, String> tcoUf;

    @FXML
    TableColumn<Cliente, String> tcoCpf;

    @FXML
    TableColumn<Cliente, String> tcoDataCadastro;

    @FXML
    TextField txtId;

    @FXML
    TextField txtNome;

    @FXML
    TextField txtEndereco;

    @FXML
    TextField txtCidade;

    @FXML
    TextField txtUf;

    @FXML
    TextField txtCpf;

    @FXML
    DatePicker dtpDataCadastro;

    ClienteDAO cDAO;
    Cliente cClicked; // Armazena o cliente clicado na tabela.

    private boolean flagNovo;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Vincula as colunas da TableView à classe Cliente.
        bindColumns();

        // Busca Dados
        carregaDados();
    }

    /**
     * Conecta as colunas da TableView com os atributos da classe Cliente.
     */
    private void bindColumns() {
        tcoId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tcoId.setStyle("-fx-alignment: CENTER_RIGHT;");

        tcoNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        tcoEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));

        tcoCidade.setCellValueFactory(new PropertyValueFactory<>("cidade"));

        tcoUf.setCellValueFactory(new PropertyValueFactory<>("uf"));
        tcoUf.setStyle("-fx-alignment: CENTER;");

        tcoCpf.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        tcoCpf.setStyle("-fx-alignment: CENTER_RIGHT;");

        tcoDataCadastro.setCellValueFactory(new PropertyValueFactory<>("dataCadastro"));
        tcoDataCadastro.setStyle("-fx-alignment: CENTER_RIGHT;");
    }

    /**
     * Carrega os dados de Cliente que estão na base e vincula a TableView.
     */
    public void carregaDados() {
        this.cDAO = new ClienteDAO();

        // Carrega view tblCliente com todos os resultados de cliente.
        this.tblCliente.getItems().setAll(cDAO.getResults());
    }

    /**
     * Action do botão btnNovo. Limpa os campos e posiciona o focus em txtNome.
     */
    @FXML
    private void btnNovoClick() {
        txtId.setText("auto");
        txtId.setEditable(false);

        txtNome.setText("");
        txtNome.requestFocus();

        txtEndereco.setText("");
        txtCidade.setText("");
        txtUf.setText("");
        txtCpf.setText("");
        dtpDataCadastro.setValue(null);

        this.flagNovo = true;
    }

    /**
     * Gerencia o click na tabela, identificando qual registro foi selecionado e
     * preenchendo os TextFields.
     */
    @FXML
    public void tblClienteOnMouseClicked() {
        this.cClicked = tblCliente.getSelectionModel().getSelectedItem();

        if (cClicked != null) {
            txtId.setText(String.valueOf(cClicked.getId()));
            txtId.setEditable(false);

            txtNome.setText(cClicked.getNome());
            txtEndereco.setText(cClicked.getEndereco());
            txtCidade.setText(cClicked.getCidade());
            txtUf.setText(cClicked.getUf());
            txtCpf.setText(cClicked.getCpf());

            // Tratamento da data do cadastro:
            if (cClicked.getDataCadastro() != null && !cClicked.getDataCadastro().isEmpty()) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                formatter = formatter.withLocale(Locale.US);
                LocalDate date = LocalDate.parse(cClicked.getDataCadastro(), formatter);

                dtpDataCadastro.setValue(date);
                dtpDataCadastro.setEditable(false);
            }

            this.flagNovo = false;
        }
    }

    @FXML
    public void btnSalvarClick() {
        Cliente cAux = new Cliente();

        cAux.setNome(txtNome.getText());
        cAux.setEndereco(txtEndereco.getText());
        cAux.setCidade(txtCidade.getText());
        cAux.setUf(txtUf.getText());
        cAux.setCpf(txtCpf.getText());

        if (this.flagNovo) {
            // Novo cliente...
            this.cDAO.create(cAux);
        } else {
            // Atualização de cliente existente...
            cAux.setId(Integer.parseInt(txtId.getText()));
            this.cDAO.update(cAux);
        }

        this.carregaDados();
    }

    @FXML
    public void btnExcluirClick() {
        if (!this.flagNovo) {
            cDAO.delete(Integer.valueOf(txtId.getText()));
            this.carregaDados();
        }
    }
    @FXML
    public void btnSairClick(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}


















// PARA O DIA 19/12/2016 ==

// JA CONSEGUI DAR PLAY E FAZER A TABELA APARECER. SO FALTA COLOCAR UM BANCO DE DADOS PRA CLIENTE, AS INFORMAÇÕES E TESTAS SE AS FUNÇÕES DO BOTÃO FUNCIONA