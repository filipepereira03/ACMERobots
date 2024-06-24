package aplicacao;

import dados.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

public class ACMERobots {
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private JTextArea relatorioText;
    private ArrayList<Robo> robos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Locacao> locacoes;
    private DefaultComboBoxModel<Robo> roboModel;
    private DefaultComboBoxModel<Cliente> clienteModel;
    private DefaultComboBoxModel<Locacao> locacaoModel;
    private ArrayList<Robo> robosSelecionados;

    public ACMERobots() {
        robos = new ArrayList<>();
        clientes = new ArrayList<>();
        locacoes = new ArrayList<>();
        robosSelecionados = new ArrayList<>();

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

        panelMenu.add(new JLabel("Bem vindo à ACMERobots"), BorderLayout.NORTH);
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));
        buttonsPanel.add(cadastrarRobo);
        buttonsPanel.add(cadastrarCliente);
        buttonsPanel.add(cadastrarLocacao);
        buttonsPanel.add(processarLoc);
        buttonsPanel.add(mostrarRelatorioGeral);
        panelMenu.add(buttonsPanel, BorderLayout.CENTER);

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

        cards.add(panelMenu, "menu");
        cards.add(createCadastrarRoboPanel(), "cadastrarRobo");
        cards.add(createCadastrarClientePanel(), "cadastrarCliente");
        cards.add(createCadastrarLocacaoPanel(), "cadastrarLocacao");
        cards.add(createProcessarLocacaoPanel(), "processarLocacao");
        cards.add(createRelatorioGeralPanel(), "relatorioGeral");

        frame.add(cards);
        frame.setVisible(true);
    }

    private JPanel createCadastrarRoboPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de cadastro de robôs", JLabel.CENTER), BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
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
        JLabel diasLabel = new JLabel("Dias de locação:");
        JTextField diasField = new JTextField(10);
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
        panelForm.add(diasLabel);
        panelForm.add(diasField);
        panelForm.add(cadastrarButton);
        panelForm.add(voltarButton);

        panel.add(panelForm, BorderLayout.CENTER);

        comboTipo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String tipo = (String) comboTipo.getSelectedItem();
                switch (tipo) {
                    case "Doméstico":
                        nivelField.setEnabled(true);
                        areaField.setEnabled(false);
                        break;
                    case "Agrícola":
                        nivelField.setEnabled(false);
                        areaField.setEnabled(true);
                        break;
                    case "Industrial":
                        nivelField.setEnabled(false);
                        areaField.setEnabled(false);
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
                    String tipo = (String) comboTipo.getSelectedItem();

                    int nivel = nivelField.isEnabled() ? Integer.parseInt(nivelField.getText()) : 0;
                    double area = areaField.isEnabled() ? Double.parseDouble(areaField.getText()) : 0;
                    int dias = Integer.parseInt(diasField.getText().trim());

                    cadastrarRobo(id, modelo, tipo, dias, nivel, area);

                    idField.setText("");
                    modeloField.setText("");
                    diasField.setText("");
                    nivelField.setText("");
                    areaField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
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

        return panel;
    }

    private JPanel createCadastrarClientePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de cadastro de clientes", JLabel.CENTER), BorderLayout.NORTH);
        JPanel panelForm = new JPanel(new GridLayout(7, 2,5,5));
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
                    String tipo = (String) comboTipo.getSelectedItem();

                    String cpf = textCPF.getText().trim();
                    int ano = tipo.equals("Empresarial") ? Integer.parseInt(textAno.getText().trim()) : 0;

                    cadastrarCliente(codigo, nome, tipo, cpf, ano);

                    textCodigo.setText("");
                    textNome.setText("");
                    textCPF.setText("");
                    textAno.setText("");
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

        textCPF.setEnabled(true);
        textAno.setEnabled(false);

        return panel;
    }

    // Arrumar o botão de adicionar robôs, que acho que não está fazendo nada
    // Tentar fazer o JCombo limpar os robôs que foram adicionados já
    private JPanel createCadastrarLocacaoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de cadastro de locações"), BorderLayout.NORTH);

        JPanel panelForm = new JPanel(new GridLayout(7, 2, 5, 5));
        JLabel labelNumero = new JLabel("Número:");
        JTextField textNumero = new JTextField(10);
        JLabel labelCliente = new JLabel("Cliente:");

        clienteModel = new DefaultComboBoxModel<>();
        for (Cliente c : clientes) {
            clienteModel.addElement(c);
        }
        JComboBox<Cliente> comboCliente = new JComboBox<>(clienteModel);

        JLabel labelRobo = new JLabel("Robô:");
        roboModel = new DefaultComboBoxModel<>();
        JComboBox<Robo> comboRobo = new JComboBox<>(roboModel);

        JButton adicionarRoboButton = new JButton("Adicionar Robô");
        JButton cadastrarButton = new JButton("Cadastrar");
        JButton voltarButton = new JButton("Voltar");

        panelForm.add(labelNumero);
        panelForm.add(textNumero);
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
                robosSelecionados.add(r);
                roboModel.removeElement(r);
                JOptionPane.showMessageDialog(frame, "Robô adicionado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int numero = Integer.parseInt(textNumero.getText().trim());
                    Cliente cliente = (Cliente) comboCliente.getSelectedItem();

                    for (Locacao l : locacoes) {
                        if (l.getNumero() == numero) {
                            JOptionPane.showMessageDialog(frame, "Número de locação já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                    }

                    Locacao locacao = new Locacao(numero, Status.CADASTRADA, null, 0, cliente, robosLocacao);
                    locacoes.add(locacao);
                    updateLocacaoModel();
                    robosSelecionados.clear();
                    JOptionPane.showMessageDialog(frame, "Locação cadastrada com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    textNumero.setText("");
                    robosLocacao.clear();
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

        JButton processarButton = new JButton("Processar locação");
        JButton voltarButton = new JButton("Voltar");

        locacaoModel = new DefaultComboBoxModel<>();
        for (Locacao l : locacoes) {
            locacaoModel.addElement(l);
        }
        JComboBox<Locacao> comboLocacao = new JComboBox<>(locacaoModel);

        processarButton.addActionListener(new ActionListener() {
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

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        buttonPanel.add(comboLocacao);
        buttonPanel.add(processarButton);
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

    private void updateRoboModel() {
        roboModel.removeAllElements();
        for (Robo r : robos) {
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

    private void cadastrarRobo(int id, String modelo, String tipo,  int dias, int nivel, double area) {

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
                novoRobo = new Agricola(id, modelo, area);
                break;
            case "Industrial":
                novoRobo = new Industrial(id, modelo, "Setor");
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Tipo de robô inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
        }

        robos.add(novoRobo);
        updateRoboModel();
        double valorFinal = novoRobo.calculaLocacao(dias);
        JOptionPane.showMessageDialog(frame, "Robo cadastrado com sucesso. " +valorFinal);
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
            relatorioText.setText("");
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


}




