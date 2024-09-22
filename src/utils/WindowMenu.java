package utils;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Image;

import contaminacionImagenes.GestionImagenes;

public class WindowMenu extends JFrame {

    private JLabel imageLabel;
    private JLabel imageNameLabel;
    private JLabel imageWidthLabel;
    private JLabel imageHeightLabel;
    private BufferedImage image;

    private JButton figure1;
    private JButton figure2;
    private JButton figure3;
    private JButton figure4;
    private JButton figure5;
    private JButton figure6;

    public String imagePath = "";
    public float contaminationPercentage = 10f;

    public WindowMenu() {
        // Configurar la ventana
        setTitle("Procesamiento de Imagen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        // Crear un panel para la imagen
        imageLabel = new JLabel("Carga una imagen", JLabel.CENTER);
        imageLabel.setFont(new Font("Arial", Font.BOLD, 16));
        imageLabel.setPreferredSize(new Dimension(600, 400));
        add(imageLabel, BorderLayout.CENTER);

        // Crear un panel para las etiquetas de información de la imagen
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));

        // Crear una etiqueta para mostrar el nombre de la imagen cargada
        imageNameLabel = new JLabel("", JLabel.CENTER);
        infoPanel.add(imageNameLabel);

        // Crear etiquetas para mostrar la anchura y altura de la imagen cargada
        imageWidthLabel = new JLabel("", JLabel.CENTER);
        infoPanel.add(imageWidthLabel);

        imageHeightLabel = new JLabel("", JLabel.CENTER);
        infoPanel.add(imageHeightLabel);

        add(infoPanel, BorderLayout.NORTH);

        // Crear botones
        JButton loadButton = new JButton("Cargar Imagen");
        JButton contaminarImagen = new JButton("Contaminar Imagen");
        JButton erodeButton = new JButton("Erosionar");
        JButton dilateButton = new JButton("Dilatar");

        // Deshabilitar botones al inicio de la ejecución del programa
        contaminarImagen.setEnabled(false);
        erodeButton.setEnabled(false);
        dilateButton.setEnabled(false);

        // Checkbox para activar el modo paralelo
        JCheckBox paralelOptionBox = new JCheckBox("Modo paralelo");
        paralelOptionBox.setSelected(true);
        
        // Panel para botones
        JPanel buttonPanel = new JPanel();

        buttonPanel.add(loadButton);
        buttonPanel.add(contaminarImagen);
        buttonPanel.add(erodeButton);
        buttonPanel.add(dilateButton);
        buttonPanel.add(paralelOptionBox);
        add(buttonPanel, BorderLayout.SOUTH);

        // Crear un panel para el título y las figuras
        JPanel figureContainerPanel = new JPanel(new BorderLayout());

        // Título de las figuras
        JLabel titleLabel = new JLabel("Elementos Estructurantes", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        figureContainerPanel.add(titleLabel, BorderLayout.NORTH);

        // Botones de las figuras
        figure1 = new JButton();
        figure2 = new JButton();
        figure3 = new JButton();
        figure4 = new JButton();
        figure5 = new JButton();
        figure6 = new JButton();

        // Nombres de las figuras
        String[] figureNames = { "Figura 1", "Figura 2", "Figura 3", "Figura 4", "Figura 5", "Figura 6" };

        // Método para configurar los botones
        configureButton(figure1);
        configureButton(figure2);
        configureButton(figure3);
        configureButton(figure4);
        configureButton(figure5);
        configureButton(figure6);

        // Cargar las imágenes de las figuras
        try {
            Image img1 = ImageIO.read(getClass().getResource("/images/figures/figure1.png"));
            Image img2 = ImageIO.read(getClass().getResource("/images/figures/figure2.png"));
            Image img3 = ImageIO.read(getClass().getResource("/images/figures/figure3.png"));
            Image img4 = ImageIO.read(getClass().getResource("/images/figures/figure4.png"));
            Image img5 = ImageIO.read(getClass().getResource("/images/figures/figure5.png"));
            Image img6 = ImageIO.read(getClass().getResource("/images/figures/figure6.png"));

            figure1.setIcon(new ImageIcon(img1.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            figure2.setIcon(new ImageIcon(img2.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            figure3.setIcon(new ImageIcon(img3.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            figure4.setIcon(new ImageIcon(img4.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            figure5.setIcon(new ImageIcon(img5.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
            figure6.setIcon(new ImageIcon(img6.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));

        } catch (Exception ex) {
            System.out.println(ex);
        }

        // Panel para figuras con gaps mínimos
        JPanel figurePanel = new JPanel(new GridLayout(6, 2, 1, 1));

        // Agregar figuras y nombres al panel
        JButton[] figures = { figure1, figure2, figure3, figure4, figure5, figure6 };
        for (int i = 0; i < figures.length; i++) {
            JPanel figureContainer = new JPanel(new BorderLayout());
            figureContainer.add(figures[i], BorderLayout.CENTER);
            JLabel figureNameLabel = new JLabel(figureNames[i], JLabel.CENTER);
            figureNameLabel.setFont(new Font("Arial", Font.PLAIN, 10)); // Reducir el tamaño de la fuente
            figureContainer.add(figureNameLabel, BorderLayout.SOUTH);
            figurePanel.add(figureContainer);
        }

        // Etiqueta para mostrar el nombre de la figura seleccionada
        JLabel selectedFigureLabelTitle = new JLabel("Figura seleccionada: ", JLabel.CENTER);
        JLabel selectedFigureLabel = new JLabel("Figura 1", JLabel.CENTER);
        figurePanel.add(selectedFigureLabelTitle);
        figurePanel.add(selectedFigureLabel);

        // Agregar panel de figuras al contenedor
        figureContainerPanel.add(figurePanel, BorderLayout.CENTER);
        add(figureContainerPanel, BorderLayout.EAST);

        // Estilo de fondo normal y seleccionado
        Color normalBackground = Color.LIGHT_GRAY;
        Color selectedBackground = Color.ORANGE;

        // Acción para mostrar el nombre de la figura seleccionada
        for (int i = 0; i < figures.length; i++) {
            final int index = i;
            if (i == 0) {
                figures[i].setBackground(selectedBackground);
            } else {

                figures[i].setBackground(normalBackground);
            }
            figures[i].setOpaque(true);
            figures[i].setFocusPainted(false);
            figures[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (JButton figure : figures) {
                        figure.setBackground(normalBackground);
                    }
                    figures[index].setBackground(selectedBackground);
                    selectedFigureLabel.setText(figureNames[index]);
                    figures[index].repaint();
                }
            });
        }

        // Acción para cargar imagen
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        image = ImageIO.read(file);
                        ImageIcon icon = new ImageIcon(getScaledImage(image, 600, 400));
                        imageLabel.setIcon(icon);
                        imageLabel.setText(null);
                        imageNameLabel.setText("Imagen: " + file.getName());
                        imageWidthLabel.setText("Ancho: " + image.getWidth() + "px");
                        imageHeightLabel.setText("Altura: " + image.getHeight() + "px");

                        // Habilitar botones
                        contaminarImagen.setEnabled(true);
                        erodeButton.setEnabled(true);
                        dilateButton.setEnabled(true);

                        // Guardar la ruta de la imagen
                        imagePath = file.getAbsolutePath();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al cargar la imagen");
                    }
                }
            }
        });

        // LISTENERS

        contaminarImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionImagenes gestionImagenes = new GestionImagenes(imagePath);

            }
        });

        // Acciones para los botones "Erosionar" y "Dilatar"
        erodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Erosionar aún no implementado.");
            }
        });

        dilateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Dilatar aún no implementado.");
            }
        });
    }

    // Método para configurar los botones
    private void configureButton(JButton button) {
        button.setBackground(Color.GRAY);
        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        button.setBackground(new Color(0, 0, 0, 128));

    }

    // Método para escalar la imagen manteniendo las proporciones
    private Image getScaledImage(BufferedImage srcImg, int maxWidth, int maxHeight) {
        int originalWidth = srcImg.getWidth();
        int originalHeight = srcImg.getHeight();

        // Calcular proporción
        double aspectRatio = (double) originalWidth / originalHeight;

        // Ajustar ancho o alto según la proporción
        int newWidth = maxWidth;
        int newHeight = (int) (maxWidth / aspectRatio);

        if (newHeight > maxHeight) {
            newHeight = maxHeight;
            newWidth = (int) (maxHeight * aspectRatio);
        }

        return srcImg.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
    }
}