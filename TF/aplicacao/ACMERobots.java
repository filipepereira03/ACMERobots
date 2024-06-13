package aplicacao;

import dados.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ACMERobots  {
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
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Tela de cadastro de robos"), BorderLayout.NORTH);
        // Adicionar o cadastro aqui
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


}
