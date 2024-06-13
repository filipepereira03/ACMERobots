package aplicacao;

import dados.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ACMERobots {
    private JFrame frame;
    private JPanel cards;
    private CardLayout cardLayout;
    private ArrayList<Robo> robos;
    private ArrayList<Cliente> clientes;
    private ArrayList<Locacao> locacoes;

    public ACMERobots() {
        robos = new ArrayList<>();
        clientes = new ArrayList<>();
        locacoes = new ArrayList<>();

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

        panelMenu.add(new JLabel("Bem vindo à ACMERobots"), BorderLayout.NORTH);
        JPanel buttonsPanel = new JPanel(new GridLayout(3, 1));
        buttonsPanel.add(cadastrarRobo);
        buttonsPanel.add(cadastrarCliente);
        buttonsPanel.add(cadastrarLocacao);
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

        cards.add(panelMenu, "menu");
        cards.add(createCadastrarRoboPanel(), "cadastrarRobo");
        cards.add(createCadastrarClientePanel(), "cadastrarCliente");
        cards.add(createCadastrarLocacaoPanel(), "cadastrarLocacao");

        frame.add(cards);
        frame.setVisible(true);
    }

    private JPanel createCadastrarRoboPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField idField = new JTextField();
        idField.setPreferredSize(new Dimension(100, 20));

        JLabel modeloLabel = new JLabel("Modelo:");
        modeloLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField modeloField = new JTextField();
        modeloField.setPreferredSize(new Dimension(100, 20));

        JLabel nivelLabel = new JLabel("Nível (para Doméstico):");
        nivelLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField nivelField = new JTextField();
        nivelField.setPreferredSize(new Dimension(100, 20));

        JLabel areaLabel = new JLabel("Área (para Agrícola):");
        areaLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField areaField = new JTextField();
        areaField.setPreferredSize(new Dimension(100, 20));

        JLabel diasLabel = new JLabel("Dias de Locação:");
        diasLabel.setHorizontalAlignment(JLabel.RIGHT);
        JTextField diasField = new JTextField();
        diasField.setPreferredSize(new Dimension(100, 20));

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int id = Integer.parseInt(idField.getText());
                    String modelo = modeloField.getText();
                    int dias = Integer.parseInt(diasField.getText());
                    int nivel = Integer.parseInt(nivelField.getText());
                    double area = Double.parseDouble(areaField.getText());

                    cadastrarRobo(id, modelo, dias, nivel, area);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JButton voltarButton = new JButton("Voltar");
        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cards, "menu");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(modeloLabel, gbc);
        gbc.gridx = 1;
        panel.add(modeloField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(nivelLabel, gbc);
        gbc.gridx = 1;
        panel.add(nivelField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(areaLabel, gbc);
        gbc.gridx = 1;
        panel.add(areaField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(diasLabel, gbc);
        gbc.gridx = 1;
        panel.add(diasField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(cadastrarButton, gbc);

        gbc.gridy = 6;
        panel.add(voltarButton, gbc);

        return panel;
    }


    private JPanel createCadastrarClientePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de cadastro de clientes"), BorderLayout.NORTH);
        // Adicionar o cadastro aqui
        return panel;
    }

    private JPanel createCadastrarLocacaoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de cadastro de locações"), BorderLayout.NORTH);
        // Adicionar o cadastro aqui
        return panel;
    }

    private void cadastrarRobo(int id, String modelo, int dias, int nivel, double area) {

        for (Robo r : robos) {
            if (r.getId() == id) {
                JOptionPane.showMessageDialog(frame, "ID já cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // Tá dando erro pra calcular o valor final de locação em robos que n sejam o industrial. Boa sorte (:
        Robo novoRobo = null;
        double valorDiario = 0.0;
        if (nivel > 0) {
            switch (nivel) {
                case 1:
                    valorDiario = 10.00;
                    break;
                case 2:
                    valorDiario = 20.00;
                    break;
                case 3:
                    valorDiario = 50.00;
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Nível inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
            novoRobo = new Domestico(id, modelo, valorDiario);
        } else if (area > 0) {
            valorDiario = 10.00 * area;
            novoRobo = new Agricola(id, modelo, valorDiario, area);
        } else {
            valorDiario = 90.00;
            novoRobo = new Industrial(id, modelo, valorDiario, "");
        }

        robos.add(novoRobo);
        double valorFinal = novoRobo.calculaLocacao(dias);
        JOptionPane.showMessageDialog(frame, "Robo cadastrado com sucesso. Valor final da locação: " + novoRobo.calculaLocacao(dias) + " Sucesso");
    }
}


