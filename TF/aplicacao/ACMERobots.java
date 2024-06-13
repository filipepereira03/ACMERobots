package aplicacao;

import dados.Agricola;
import dados.Domestico;
import dados.Industrial;
import dados.Robo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ACMERobots  {
    private JFrame frame;
    private JPanel panel;
    private JTextField idField, modeloField, valorDiarioField, tipoField, setorField;
    private Map<Integer, Robo> robos;
    private ArrayList<Robo> listaRobos;

    public ACMERobots() {
        robos = new TreeMap<>();
        listaRobos = new ArrayList<>();
        initGUI();
    }

    private void initGUI() {
        frame = new JFrame("ACME Robots");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setBounds(10, 20, 80, 25);
        panel.add(idLabel);

        JLabel modeloLabel = new JLabel("Modelo:");
        modeloLabel.setBounds(10, 50, 80, 25);
        panel.add(modeloLabel);

        JLabel valorDiarioLabel = new JLabel("Valor Diário:");
        valorDiarioLabel.setBounds(10, 80, 80, 25);
        panel.add(valorDiarioLabel);

        JLabel tipoLabel = new JLabel("Tipo:");
        tipoLabel.setBounds(10, 110, 80, 25);
        panel.add(tipoLabel);

        idField = new JTextField(20);
        idField.setBounds(100, 20, 165, 25);
        panel.add(idField);

        JButton cadastrarButton = new JButton("Cadastrar");
        cadastrarButton.setBounds(10, 140, 80, 25);
        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarRobo();
            }
        });
        panel.add(cadastrarButton);
    }

    private void cadastrarRobo() {
        try {
        int id = Integer.parseInt(idField.getText());
        String modelo = modeloField.getText();
        double valorDiario = Double.parseDouble(valorDiarioField.getText());
        int tipo = Integer.parseInt(tipoField.getText());

        for (Robo robo : listaRobos) {
            if (robo.getId() == id) {
                JOptionPane.showMessageDialog(frame, "Robo já cadastrado", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        Robo robo = null;
        switch (tipo) {
            case 1:
                robo = new Domestico(id, modelo, valorDiario);
                break;
            case 2:
                robo = new Industrial(id, modelo, valorDiario, "");
                break;
            case 3:
                double area = Double.parseDouble(JOptionPane.showInputDialog(frame, "Área (m2):"));
                robo = new Agricola(id, modelo, valorDiario, area);
                break;
            default:
                JOptionPane.showMessageDialog(frame, "Tipo de robo inválido", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
        }

        listaRobos.add(robo);
        JOptionPane.showMessageDialog(frame, "Robo cadastrado com sucesso", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Valor inválido", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
