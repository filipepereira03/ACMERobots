package aplicacao;

import dados.*;

import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class ACMERobots implements Serializable {
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private JTextArea relatorioText;
    private JTextArea locacoesText;
    private ArrayList<Robo> robos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Locacao> locacoes;
    private Queue<Locacao> locacoesPendentes;
    private DefaultComboBoxModel<Robo> roboModel;
    private DefaultComboBoxModel<Cliente> clienteModel;
    private DefaultComboBoxModel<Locacao> locacaoModel;
    private DefaultComboBoxModel<Locacao> locacaoModelPendente;
    private ArrayList<Robo> robosSelecionados;
    private ArrayList<Robo> robosDisponiveis;


    public ACMERobots() {
        robos = new ArrayList<>();
        clientes = new ArrayList<>();
        locacoes = new ArrayList<>();
        robosSelecionados = new ArrayList<>();
        robosDisponiveis = new ArrayList<>();
        locacoesPendentes = new LinkedList<>();
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("ACME Robots");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        JPanel panelMenu = new JPanel(new BorderLayout());
        JButton cadastrarRobo = new JButton("Cadastrar Robo");
        JButton cadastrarCliente = new JButton("Cadastrar Cliente");
        JButton cadastrarLocacao = new JButton("Cadastrar Locação");
        JButton processarLoc = new JButton("Processar Locação");
        JButton mostrarRelatorioGeral = new JButton("Relatório Geral");
        JButton consultarLocacoes = new JButton("Consultar Locações");
        JButton alterarStatus = new JButton("Alterar status da Locação");
        JButton carregarDadosIniciais = new JButton("Carregar dados iniciais:");
        JButton salvarDados = new JButton("Salvar dados");
        JButton carregarDados = new JButton("Carregar dados");
        JButton finalizarSistema = new JButton("Finalizar Sistema");

        panelMenu.add(new JLabel("Bem vindo à ACMERobots"), BorderLayout.NORTH);
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));
        buttonsPanel.add(cadastrarRobo);
        buttonsPanel.add(cadastrarCliente);
        buttonsPanel.add(cadastrarLocacao);
        buttonsPanel.add(processarLoc);
        buttonsPanel.add(mostrarRelatorioGeral);
        buttonsPanel.add(consultarLocacoes);
        buttonsPanel.add(alterarStatus);
        buttonsPanel.add(carregarDadosIniciais);
        buttonsPanel.add(salvarDados);
        buttonsPanel.add(carregarDados);
        buttonsPanel.add(finalizarSistema);
        panelMenu.add(buttonsPanel, BorderLayout.CENTER);

        finalizarSistema.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        carregarDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileInputStream fileIn = new FileInputStream("dados.ser");
                    ObjectInputStream in = new ObjectInputStream(fileIn);
                    robos = (ArrayList<Robo>) in.readObject();
                    clientes = (ArrayList<Cliente>) in.readObject();
                    locacoes = (ArrayList<Locacao>) in.readObject();
                    updateRoboModel();
                    updateLocacaoModel();
                    updateClienteModel();
                    in.close();
                    fileIn.close();

                    JOptionPane.showMessageDialog(frame, "Dados carregados com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao carregar os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        salvarDados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    FileOutputStream fileOut = new FileOutputStream("dados.ser");
                    ObjectOutputStream out = new ObjectOutputStream(fileOut);
                    out.writeObject(robos);
                    out.writeObject(clientes);
                    out.writeObject(locacoes);
                    out.close();
                    fileOut.close();
                    JOptionPane.showMessageDialog(frame, "Dados salvos com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Erro ao salvar os dados.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        cadastrarRobo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "cadastrarRobo");
            }
        });

        cadastrarCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "cadastrarCliente");
            }
        });

        cadastrarLocacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "cadastrarLocacao");
            }
        });

        processarLoc.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "processarLocacao");
            }
        });

        mostrarRelatorioGeral.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "relatorioGeral");
                mostrarRelatorioGeral();
            }
        });

        consultarLocacoes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "consultarLocacoes");
                consultarTodasLocacoes();
            }
        });

        alterarStatus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "alterarStatus");
            }
        });

        carregarDadosIniciais.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "carregarDadosIniciais");
            }
        });

        cards.add(panelMenu, "menu");
        cards.add(createCadastrarRoboPanel(), "cadastrarRobo");
        cards.add(createCadastrarClientePanel(), "cadastrarCliente");
        cards.add(createCadastrarLocacaoPanel(), "cadastrarLocacao");
        cards.add(createProcessarLocacaoPanel(), "processarLocacao");
        cards.add(createRelatorioGeralPanel(), "relatorioGeral");
        cards.add(createMostrarLocacoesPanel(), "consultarLocacoes");
        cards.add(createAlterarLocacoesPanel(), "alterarStatus");
        cards.add(createCarregarDadosIniciaisPanel(), "carregarDadosIniciais");


        frame.add(cards);
        frame.setVisible(true);
    }

    private JPanel createCadastrarRoboPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de cadastro de robôs", JLabel.CENTER), BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(8, 2, 5, 5));
        JLabel idLabel = new JLabel("ID:");
        JTextField idField = new JTextField(10);
        JLabel modeloLabel = new JLabel("Modelo:");
        JTextField modeloField = new JTextField(10);
        JLabel labelTipo = new JLabel("Tipo:");
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Doméstico", "Agrícola", "Industrial"});
        JLabel nivelLabel = new JLabel("Nível (Doméstico):");
        JTextField nivelField = new JTextField(10);
        JLabel areaLabel = new JLabel("Área (Agrícola):");
        JTextField areaField = new JTextField(10);
        JLabel usoLabel = new JLabel("Uso (Agrícola):");
        JTextField usoField = new JTextField(10);
        JLabel setorLabel = new JLabel("Setor (Industrial):");
        JTextField setorField = new JTextField(10);
        JButton cadastrarButton = new JButton("Cadastrar");
        JButton voltarButton = new JButton("Voltar");

        panelForm.add(idLabel);
        panelForm.add(idField);
        panelForm.add(modeloLabel);
        panelForm.add(modeloField);
        panelForm.add(labelTipo);
        panelForm.add(comboTipo);
        panelForm.add(nivelLabel);
        panelForm.add(nivelField);
        panelForm.add(areaLabel);
        panelForm.add(areaField);
        panelForm.add(usoLabel);
        panelForm.add(usoField);
        panelForm.add(setorLabel);
        panelForm.add(setorField);
        panelForm.add(cadastrarButton);
        panelForm.add(voltarButton);

        panel.add(panelForm, BorderLayout.CENTER);

        comboTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) comboTipo.getSelectedItem();
                switch (tipo) {
                    case "Doméstico":
                        areaField.setEnabled(false);
                        setorField.setEnabled(false);
                        nivelField.setEnabled(true);
                        usoField.setEnabled(false);
                        break;
                    case "Agrícola":
                        nivelField.setEnabled(false);
                        areaField.setEnabled(true);
                        setorField.setEnabled(false);
                        usoField.setEnabled(true);
                        break;
                    case "Industrial":
                        nivelField.setEnabled(false);
                        areaField.setEnabled(false);
                        setorField.setEnabled(true);
                        usoField.setEnabled(false);
                        break;
                }
            }
        });


        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String modelo = modeloField.getText();
                    if (modelo.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Campo modelo é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                        modeloField.setText("");
                        return;
                    }
                    String tipo = (String) comboTipo.getSelectedItem();

                    int nivel = 0;
                    if (nivelField.isEnabled()) {
                        nivel = Integer.parseInt(nivelField.getText().trim());
                        if (nivel < 1 || nivel > 3) {
                            JOptionPane.showMessageDialog(frame, "Nível inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                            nivelField.setText("");
                            return;
                        }
                    }
                    double area = 0;
                    if (areaField.isEnabled()) {
                        area = Double.parseDouble(areaField.getText().trim());
                        if (area <= 0) {
                            JOptionPane.showMessageDialog(frame, "Área inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                            areaField.setText("");
                            return;
                        }
                    }
                    String setor = "";
                    if (setorField.isEnabled()) {
                        setor = setorField.getText();
                        if (setor.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Campo setor é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    String uso = "";
                    if (usoField.isEnabled()) {
                        uso = usoField.getText();
                        if (uso.isEmpty()) {
                            JOptionPane.showMessageDialog(frame, "Campo uso é obrigatório", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    }


                    cadastrarRobo(id, modelo, tipo, setor, nivel, area, uso);

                    idField.setText("");
                    modeloField.setText("");
                    setorField.setText("");
                    nivelField.setText("");
                    areaField.setText("");
                    usoField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                    idField.setText("");
                    modeloField.setText("");
                    setorField.setText("");
                    nivelField.setText("");
                    areaField.setText("");
                    usoField.setText("");
                }
            }
        });
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "menu");
            }
        });

        nivelField.setEnabled(true);
        areaField.setEnabled(false);
        setorField.setEnabled(false);
        usoField.setEnabled(false);

        return panel;
    }

    private JPanel createCadastrarClientePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de cadastro de clientes", JLabel.CENTER), BorderLayout.NORTH);
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        JLabel labelCodigo = new JLabel("Código:");
        JTextField textCodigo = new JTextField(10);
        JLabel labelNome = new JLabel("Nome:");
        JTextField textNome = new JTextField(10);
        JLabel labelTipo = new JLabel("Tipo:");
        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Individual", "Empresarial"});
        JLabel labelCPF = new JLabel("CPF (Individual):");
        JTextField textCPF = new JTextField(10);
        JLabel labelAno = new JLabel("Ano de Fundação (Empresarial):");
        JTextField textAno = new JTextField(10);
        JButton cadastrarButton = new JButton("Cadastrar");
        JButton voltarButton = new JButton("Voltar");

        panelForm.add(labelCodigo);
        panelForm.add(textCodigo);
        panelForm.add(labelNome);
        panelForm.add(textNome);
        panelForm.add(labelTipo);
        panelForm.add(comboTipo);
        panelForm.add(labelCPF);
        panelForm.add(textCPF);
        panelForm.add(labelAno);
        panelForm.add(textAno);
        panelForm.add(cadastrarButton);
        panelForm.add(voltarButton);

        panel.add(panelForm, BorderLayout.CENTER);

        comboTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) comboTipo.getSelectedItem();
                if ("Individual".equals(tipo)) {
                    textCPF.setEnabled(true);
                    textAno.setEnabled(false);
                } else {
                    textCPF.setEnabled(false);
                    textAno.setEnabled(true);
                }
            }
        });

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int codigo = Integer.parseInt(textCodigo.getText().trim());
                    String nome = textNome.getText().trim();
                    if (nome.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Campo nome é obrigatório.", "Erro", JOptionPane.ERROR_MESSAGE);
                        textNome.setText("");
                        return;
                    }
                    String tipo = (String) comboTipo.getSelectedItem();

                    String cpf = "";
                    if (textCPF.isEnabled()) {
                        cpf = textCPF.getText().trim();
                        if (cpf.matches("[0-9]+")) {
                            cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
                        } else {
                            JOptionPane.showMessageDialog(frame, "CPF inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                            textCPF.setText("");
                            return;
                        }
                    }
                    int ano = tipo.equals("Empresarial") ? Integer.parseInt(textAno.getText().trim()) : 0;

                    cadastrarCliente(codigo, nome, tipo, cpf, ano);

                    textCodigo.setText("");
                    textNome.setText("");
                    textCPF.setText("");
                    textAno.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                    textCodigo.setText("");
                    textNome.setText("");
                    textCPF.setText("");
                    textAno.setText("");
                }
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "menu");
            }
        });

        textCPF.setEnabled(true);
        textAno.setEnabled(false);

        return panel;
    }

    private JPanel createCadastrarLocacaoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de cadastro de locações"), BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        JLabel labelNumero = new JLabel("Número:");
        JTextField textNumero = new JTextField(10);
        JLabel labelDataInicio = new JLabel("Data inicio:");
        JTextField textDataInicio = new JTextField(10);
        JLabel labelDataFim = new JLabel("Data fim:");
        JTextField textDataFim = new JTextField(10);
        JLabel labelCliente = new JLabel("Cliente:");
        clienteModel = new DefaultComboBoxModel<>();
        for (Cliente c : clientes) {
            clienteModel.addElement(c);
        }
        JComboBox<Cliente> comboCliente = new JComboBox<>(clienteModel);

        JLabel labelRobo = new JLabel("Robô:");
        roboModel = new DefaultComboBoxModel<>();
        updateRoboModel();
        JComboBox<Robo> comboRobo = new JComboBox<>(roboModel);

        JButton adicionarRoboButton = new JButton("Adicionar Robô");
        JButton cadastrarButton = new JButton("Cadastrar");
        JButton voltarButton = new JButton("Voltar");

        panelForm.add(labelNumero);
        panelForm.add(textNumero);
        panelForm.add(labelDataInicio);
        panelForm.add(textDataInicio);
        panelForm.add(labelDataFim);
        panelForm.add(textDataFim);
        panelForm.add(labelCliente);
        panelForm.add(comboCliente);
        panelForm.add(labelRobo);
        panelForm.add(comboRobo);
        panelForm.add(adicionarRoboButton);
        panelForm.add(cadastrarButton);
        panelForm.add(voltarButton);

        panel.add(panelForm, BorderLayout.CENTER);

        ArrayList<Robo> robosLocacao = new ArrayList<>();

        adicionarRoboButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Robo r = (Robo) comboRobo.getSelectedItem();
                if (r != null) {
                    robosLocacao.add(r);
                    robosDisponiveis.remove(r);
                    updateRoboModel();
                    JOptionPane.showMessageDialog(frame, "Robô adicionado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numero = Integer.parseInt(textNumero.getText().trim());
                    Date dataInicio = new Date();
                    Calendar c = Calendar.getInstance();
                    c.setTime(dataInicio);
                    c.add(Calendar.DAY_OF_MONTH, 7);
                    long millis = c.getTimeInMillis();
                    int dataFim = (int) (millis / (1000 * 60 * 60 * 24));

                    Cliente cliente = (Cliente) comboCliente.getSelectedItem();

                    if (clientes.isEmpty()) {
                        JOptionPane.showMessageDialog(frame, "Selecione um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    for (Locacao l : locacoes) {
                        if (l.getNumero() == numero) {
                            JOptionPane.showMessageDialog(frame, "Número de locação já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    Locacao locacao = new Locacao(numero, Status.CADASTRADA, dataInicio, dataFim, cliente, robosLocacao);
                    locacoes.add(locacao);
                    updateLocacaoModel();
                    robosLocacao.clear();
                    JOptionPane.showMessageDialog(frame, "Locação cadastrada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    textNumero.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "menu");
            }
        });


        return panel;
    }

    private JPanel createProcessarLocacaoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de processamento de locações"), BorderLayout.NORTH);

        JButton processarManualButton = new JButton("Processar locação");
        JButton voltarButton = new JButton("Voltar");

        locacaoModel = new DefaultComboBoxModel<>();
        for (Locacao l : locacoes) {
            locacaoModel.addElement(l);
        }
        JComboBox<Locacao> comboLocacao = new JComboBox<>(locacaoModel);


        processarManualButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                processarLocacoes();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "menu");
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        buttonPanel.add(comboLocacao);
        buttonPanel.add(processarManualButton);
        buttonPanel.add(voltarButton);

        panel.add(buttonPanel, BorderLayout.CENTER);

        return panel;
    }


    private JPanel createRelatorioGeralPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Relatório Geral"), BorderLayout.NORTH);
        relatorioText = new JTextArea();
        relatorioText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(relatorioText);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "menu");
            }
        });
        panel.add(voltarButton, BorderLayout.SOUTH);


        return panel;
    }

    private JPanel createMostrarLocacoesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Consultar Locações"), BorderLayout.NORTH);
        locacoesText = new JTextArea();
        locacoesText.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(locacoesText);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "menu");
            }
        });
        panel.add(voltarButton, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createAlterarLocacoesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Alterar status da locação"), BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        JLabel labelNumero = new JLabel("Número da Locação: ");
        JTextField textNumero = new JTextField(10);

        JLabel labelNovaSituacao = new JLabel("Nova Situação: ");
        JComboBox<String> comboSituacao = new JComboBox<>(new String[]{"CADASTRADA", "EXECUTANDO", "FINALIZADA", "CANCELADA"});

        JButton alterarButton = new JButton("Alterar");
        JButton voltarButton = new JButton("Voltar");

        panelForm.add(labelNumero);
        panelForm.add(textNumero);
        panelForm.add(labelNovaSituacao);
        panelForm.add(comboSituacao);
        panelForm.add(alterarButton);
        panelForm.add(voltarButton);

        panel.add(panelForm, BorderLayout.CENTER);

        alterarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numeroLoc = Integer.parseInt(textNumero.getText().trim());
                    String novaSituacaoStr = (String) comboSituacao.getSelectedItem();
                    if (novaSituacaoStr == null) {
                        JOptionPane.showMessageDialog(frame, "Selecione uma nova situação.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    Status novaSituacao = Status.valueOf(novaSituacaoStr);

                    Locacao loc = null;
                    for (Locacao l : locacoes) {
                        if (l.getNumero() == numeroLoc) {
                            loc = l;
                            break;
                        }
                    }

                    if (loc == null) {
                        JOptionPane.showMessageDialog(frame, "Locação não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (loc.getStatus() == Status.FINALIZADA || loc.getStatus() == Status.CANCELADA) {
                        JOptionPane.showMessageDialog(frame, "Não é possível alterar a situação de uma locação finalizada ou cancelada.", "Erro", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    loc.setStatus(novaSituacao);
                    JOptionPane.showMessageDialog(frame, "Situação da locação alterada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    consultarTodasLocacoes();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Entrada inválida.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "menu");
            }
        });
        return panel;
    }

    private JPanel createCarregarDadosIniciaisPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Carregar dados iniciais"), BorderLayout.NORTH);
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        JButton carregarButtonRobo = new JButton("Carregar Robos");
        JButton carregarButtonCliente = new JButton("Carregar Clientes");
        JButton carregarButtonLocacao = new JButton("Carregar Locações");
        JButton voltarButton = new JButton("Voltar");
        panelForm.add(carregarButtonRobo);
        panelForm.add(carregarButtonCliente);
        panelForm.add(carregarButtonLocacao);
        panelForm.add(voltarButton);
        panel.add(panelForm, BorderLayout.CENTER);

        carregarButtonRobo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSVReader csv = new CSVReader();
                String input = JOptionPane.showInputDialog(frame, "Digite o nome do arquivo:");
                String arq = input + ".CSV";
                List<String[]> dados = csv.fileReader(arq);
                for (String[] dado : dados) {
                    System.out.println(Arrays.toString(dado));
                    int id = Integer.parseInt(dado[0]);
                    String modelo = dado[1];
                    int tipo = Integer.parseInt(dado[2]);
                    switch (tipo) {
                        case 1:
                            String tipoStr = "Doméstico";
                            int nivel = Integer.parseInt(dado[3]);
                            cadastrarRobo(id, modelo, tipoStr, "", nivel, 0, "");
                            break;
                        case 2:
                            String tipoStr2 = "Agrícola";
                            double area = Double.parseDouble(dado[3]);
                            cadastrarRobo(id, modelo, tipoStr2, "", 0, area, "");
                            break;
                        case 3:
                            String tipoStr3 = "Industrial";
                            String setor = dado[3];
                            cadastrarRobo(id, modelo, tipoStr3, setor, 0, 0, "");
                            break;
                    }

                }
                updateRoboModel();
            }
        });


        carregarButtonCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSVReader csv = new CSVReader();
                String input = JOptionPane.showInputDialog(frame, "Digite o nome do arquivo:");
                String arq = input + ".CSV";
                List<String[]> dados = csv.fileReader(arq);
                for (String[] dado : dados) {
                    System.out.println(Arrays.toString(dado));
                    int codigo = Integer.parseInt(dado[0]);
                    String nome = dado[1];
                    int tipo = Integer.parseInt(dado[2]);
                    switch (tipo) {
                        case 1:
                            String tipoStr = "Individual";
                            String cpf = dado[3];
                            cadastrarCliente(codigo, nome, tipoStr, cpf, 0);
                            break;
                        case 2:
                            String tipoStr2 = "Empresarial";
                            int ano = Integer.parseInt(dado[3]);
                            cadastrarCliente(codigo, nome, tipoStr2, "", ano);
                            break;
                    }
                }
                updateClienteModel();
            }
        });

        carregarButtonLocacao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CSVReader csv = new CSVReader();
                String input = JOptionPane.showInputDialog(frame, "Digite o nome do arquivo:");
                String arq = input + ".CSV";
                List<String[]> dados = csv.fileReader(arq);
                for (String[] dado : dados) {
                    System.out.println(Arrays.toString(dado));
                    int numero = Integer.parseInt(dado[0]);
                    String dataStringInicio = dado[1];
                    String dataStringFim = dado[2];
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date dataInicio = sdf.parse(dataStringInicio);
                        Date dataFim = sdf.parse(dataStringFim);
                        Calendar c = Calendar.getInstance();
                        c.setTime(dataFim);
                        c.add(Calendar.DAY_OF_MONTH, 7);
                        long millis = c.getTimeInMillis();
                        int dataFimInt = (int) (millis / (1000 * 60 * 60 * 24));
                        int codigo = Integer.parseInt(dado[3]);
                        Cliente cliente = null;
                        for (Cliente c1 : clientes) {
                            if (c1.getCodigo() == codigo) {
                                cliente = c1;
                                break;
                            }
                        }
                        if (cliente == null) {
                            JOptionPane.showMessageDialog(frame, "Cliente não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        Locacao locacao = new Locacao(numero, dataInicio, dataFimInt, cliente, new ArrayList<>());
                        locacoes.add(locacao);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(frame, "Erro ao processar datas.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
                updateLocacaoModel();
            }
        });


        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "menu");
            }
        });

        return panel;
    }

    private void updateRoboModel() {
        roboModel.removeAllElements();
        for (Robo r : robosDisponiveis) {
            roboModel.addElement(r);
        }
    }

    private void updateClienteModel() {
        clienteModel.removeAllElements();
        for (Cliente c : clientes) {
            clienteModel.addElement(c);
        }
    }

    private void updateLocacaoModel() {
        locacaoModel.removeAllElements();
        for (Locacao l : locacoes) {
            locacaoModel.addElement(l);
        }
    }

    private void updateLocacaoPendenteModel() {
        locacaoModel.removeAllElements();
        for (Locacao l : locacoesPendentes) {
            locacaoModel.addElement(l);
        }
    }

    private void cadastrarRobo(int id, String modelo, String tipo, String setor, int nivel, double area, String uso) {

        for (Robo r : robos) {
            if (r.getId() == id) {
                JOptionPane.showMessageDialog(frame, "ID já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Robo novoRobo;
        switch (tipo) {
            case "Doméstico":
                novoRobo = new Domestico(id, modelo, nivel);
                break;
            case "Agrícola":
                novoRobo = new Agricola(id, modelo, area, uso);
                break;
            case "Industrial":
                novoRobo = new Industrial(id, modelo, setor);
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Tipo de robô inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
        }

        robos.add(novoRobo);
        robosDisponiveis.add(novoRobo);
        updateRoboModel();
        JOptionPane.showMessageDialog(frame, "Robo cadastrado com sucesso. ", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void cadastrarCliente(int codigo, String nome, String tipo, String cpf, int ano) {
        for (Cliente c : clientes) {
            if (c.getCodigo() == codigo) {
                JOptionPane.showMessageDialog(frame, "Código já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Cliente novoCliente;
        if ("Individual".equals(tipo)) {
            novoCliente = new Individual(codigo, nome, cpf);
        } else {
            novoCliente = new Empresarial(codigo, nome, ano);
        }

        if (novoCliente != null) {
            clientes.add(novoCliente);
            JOptionPane.showMessageDialog(frame, "Cliente cadastrado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(frame, "Erro ao cadastrar cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        updateClienteModel();
    }

    private void processarLocacoes() {
        if (locacoes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhuma locação cadastrada.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Locacao loc = (Locacao) locacaoModel.getSelectedItem();
        if (loc == null) {
            JOptionPane.showMessageDialog(frame, "Selecione uma locação para processar.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (loc.getStatus().equals(Status.CADASTRADA)) {
            List<Robo> robosSolicitados = loc.getRobos();
            boolean todosDisponiveis = robosSolicitados.stream().allMatch(Robo::estaDisponivel);

            if (todosDisponiveis) {
                robosSolicitados.forEach(Robo::locar);
                loc.setStatus(Status.EXECUTANDO);
                JOptionPane.showMessageDialog(frame, "Locação processada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                robosSolicitados.stream().filter(Robo::estaLocado).forEach(Robo::liberar);
                loc.setStatus(Status.CADASTRADA);
                JOptionPane.showMessageDialog(frame, "Alguns robôs não estão disponíveis.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Locação não está no status 'Cadastrada'.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        updateLocacaoModel();
    }


    private void mostrarRelatorioGeral() {

        StringBuilder relatorio = new StringBuilder();

        if (robos.isEmpty() && clientes.isEmpty() && locacoes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhum dado cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            cardLayout.show(cards, "menu");
            return;
        }

        relatorio.append("Robôs:\n");
        if (robos.isEmpty()) {
            relatorio.append("Nenhum robô cadastrado.\n");
        } else {
            List<Robo> robosOrdenados = new ArrayList<>(robos);
            robosOrdenados.sort(Comparator.comparing(Robo::getId));
            for (Robo r : robosOrdenados) {
                relatorio.append(r.toString()).append("\n");
            }
        }

        relatorio.append("\nClientes:\n");
        if (clientes.isEmpty()) {
            relatorio.append("Nenhum cliente cadastrado.\n");
        } else {
            List<Cliente> clientesOrdenados = new ArrayList<>(clientes);
            clientesOrdenados.sort(Comparator.comparing(Cliente::getCodigo));
            for (Cliente c : clientesOrdenados) {
                relatorio.append(c.toString()).append("\n");
            }
        }

        relatorio.append("\nLocações:\n");
        if (locacoes.isEmpty()) {
            relatorio.append("Nenhuma locação cadastrada.\n");
        } else {
            List<Locacao> locacoesOrdenadas = new ArrayList<>(locacoes);
            locacoesOrdenadas.sort(Comparator.comparing(Locacao::getNumero));
            for (Locacao l : locacoesOrdenadas) {
                relatorio.append(l.toString()).append("\n");
            }
        }
        relatorioText.setText(relatorio.toString());
    }

    private void consultarTodasLocacoes() {
        StringBuilder relatorio = new StringBuilder();
        if (locacoes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhuma locação cadastrada.", "Erro", JOptionPane.ERROR_MESSAGE);
            cardLayout.show(cards, "menu");
            return;
        }
        for (Locacao l : locacoes) {
            relatorio.append("Número da locação: ").append(l.getNumero()).append("\n");
            relatorio.append("Cliente: ").append(l.getCliente().getNome()).append("\n");
            relatorio.append("Status: ").append(l.getStatus()).append("\n");

            List<Robo> robosLocados = l.getRobos();
            if (robosLocados == null || robosLocados.isEmpty()) {
                relatorio.append("Nenhum robô alocado.\n");
            } else {
                relatorio.append("Robôs alocados:\n");
                for (Robo r : robosLocados) {
                    relatorio.append(r.toString()).append("\n");
                }
            }
            relatorio.append("Valor final: ").append(l.calculaValorFinal()).append("\n\n");
        }
        locacoesText.setText(relatorio.toString());
    }

    private Queue<Locacao> getLocacoesPendentes() {
        for (Locacao l : locacoes) {
            if (l.getStatus() == Status.CADASTRADA) {
                locacoesPendentes.add(l);
            }
        }
        return locacoesPendentes;
    }

    private void processarLocacaoPendente() {
        Queue<Locacao> locacoesPendentes = getLocacoesPendentes();
        if (locacoesPendentes.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Nenhuma locação pendente.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Locacao loc = locacoesPendentes.poll();
        List<Robo> robosSolicitados = loc.getRobos();
        boolean todosDisponiveis = robosSolicitados.stream().allMatch(Robo::estaDisponivel);

        if (todosDisponiveis) {
            robosSolicitados.forEach(Robo::locar);
            loc.setStatus(Status.EXECUTANDO);
            JOptionPane.showMessageDialog(frame, "Locação processada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            robosSolicitados.stream().filter(Robo::estaLocado).forEach(Robo::liberar);
            loc.setStatus(Status.CADASTRADA);
            JOptionPane.showMessageDialog(frame, "Alguns robôs não estão disponíveis.", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        updateLocacaoModel();
    }
}




