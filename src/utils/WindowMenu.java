package utils;

import javax.swing.*;

import contaminacionImagenes.GestionImagenes;
import tech.ImageProcessor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

//import contaminacionImagenes.GestionImagenes;

public class WindowMenu extends JFrame {

    private JLabel imageLabel;
    private JLabel imageNameLabel;
    private JLabel imageWidthLabel;
    private JLabel imageHeightLabel;
    private BufferedImage image;
    protected BufferedImage originalImage;

    private JButton figure1;
    private JButton figure2;
    private JButton figure3;
    private JButton figure4;
    private JButton figure5;
    private JButton figure6;

    private JTextArea logArea;
    private String logHistory = "";

    public String imagePath = "";
    public float contaminationPercentage = 10f;

    private int numThreads = 3;

    ImageProcessor processor = new ImageProcessor();


    public WindowMenu() {
        // Configurar la ventana
        setTitle("Procesamiento de Imagen");
        setSize(900, 600);
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
        JButton resetImagenButton = new JButton("Reset");
        JButton loadButton = new JButton("Cargar Imagen");
        JButton contaminarImagen = new JButton("Contaminar Imagen");
        JButton erodeButton = new JButton("Erosionar");
        JButton dilateButton = new JButton("Dilatar");

        // Deshabilitar botones al inicio de la ejecución del programa
        resetImagenButton.setEnabled(false);
        contaminarImagen.setEnabled(false);
        erodeButton.setEnabled(false);
        dilateButton.setEnabled(false);

        // Checkbox para activar el modo paralelo
        JCheckBox paralelOptionBox = new JCheckBox("Modo paralelo");
        paralelOptionBox.setSelected(true);

        //input para el porcentaje de contaminación
        JLabel contaminationLabel = new JLabel("%", JLabel.RIGHT);
        JTextField contaminationInput = new JTextField(String.valueOf(contaminationPercentage));

        // Input de numero de threads
        JLabel threadLabel = new JLabel("N° Threads: ", JLabel.RIGHT);
        JTextField threadsInput = new JTextField(String.valueOf(numThreads));

        /* threadLabel.setVisible(false);
        threadsInput.setVisible(false); */
        

        // Panel para botones
        JPanel buttonPanel = new JPanel();

        // Añadir un espacio entre los botones
        
        buttonPanel.add(resetImagenButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(contaminarImagen);
        buttonPanel.add(contaminationInput);
        buttonPanel.add(contaminationLabel);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(erodeButton);
        buttonPanel.add(dilateButton);
        buttonPanel.add(paralelOptionBox);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(threadLabel);
        buttonPanel.add(threadsInput);
        
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

        // Panel derecho

        JPanel rightPanel = new JPanel(new GridLayout(2,1));

        // Panel para figuras con gaps mínimos
        JPanel figurePanel = new JPanel(new GridLayout(4, 2, 1, 1));

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
        //add(figureContainerPanel, BorderLayout.EAST);

        // Crea un textarea para hacer seguimiento de los tiempos de ejecución de cada algoritmo
        JLabel logAreaLabel = new JLabel("Logs:", JLabel.CENTER);
        logArea = new JTextArea("");
        logArea.setEditable(false);

        // Panel para hacer seguimiento de los tiempos de ejecución de los algoritmos
        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.add(logAreaLabel, BorderLayout.NORTH);
        logPanel.add(new JScrollPane(logArea), BorderLayout.CENTER);

        rightPanel.add(figureContainerPanel);
        rightPanel.add(logPanel);
        add(rightPanel, BorderLayout.EAST);

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

        // LISTENERS

        
        // Accion de resetear a imagen original

        resetImagenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                image = originalImage;
                updateImage(originalImage);
            }
        });

        // Acción para cargar imagen
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser(new File(System.getProperty("user.dir")));
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                image = ImageIO.read(file);
                originalImage = ImageIO.read(file);
                updateImage(image);
                imageNameLabel.setText("Imagen: " + file.getName());
                imageWidthLabel.setText("Ancho: " + image.getWidth() + "px");
                imageHeightLabel.setText("Altura: " + image.getHeight() + "px");

                // Habilitar botones
                resetImagenButton.setEnabled(true);
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


        // Listener para contaminationInput
        contaminationInput.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                try {
                    contaminationPercentage = Float.parseFloat(contaminationInput.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
                }
            }
        });

        contaminarImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GestionImagenes GI = new GestionImagenes(image);
                image = GI.salPimienta(contaminationPercentage);

                updateImage(image);
        
            }
        });

        // Acciones para los botones "Erosionar" y "Dilatar"
        erodeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actualizando ultima imagen cargada
                processor.setImg(image);
                ArrayList<Object> resultados;
                try {
                    if (paralelOptionBox.isSelected()) {
                        resultados = processor.processEroderP("src/images/output/output.jpg", Integer.parseInt(selectedFigureLabel.getText().split(" ")[1]),numThreads);
                        image = (BufferedImage) resultados.get(0);
                        addLog("Eroder",(long) resultados.get(1), (String) resultados.get(2));
                        updateImage(image);
                    } else {
                        resultados = processor.processEroderS("src/images/output/output.jpg", Integer.parseInt(selectedFigureLabel.getText().split(" ")[1]));
                        image = (BufferedImage) resultados.get(0);
                        addLog("Eroder",(long) resultados.get(1), (String) resultados.get(2));
                        updateImage(image);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al erosionar la imagen");
                }
            }
        });

        dilateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actualizando ultima imagen cargada
                processor.setImg(image);
                ArrayList<Object> resultados;
                try {
                    if (paralelOptionBox.isSelected()) {
                        resultados = processor.processDilatorP("src/images/output/output.jpg", Integer.parseInt(selectedFigureLabel.getText().split(" ")[1]),numThreads);
                        image = (BufferedImage) resultados.get(0);
                        addLog("Dilator",(long) resultados.get(1), (String) resultados.get(2));
                        updateImage(image);
                    } else {
                        resultados = processor.processEroderS("src/images/output/output.jpg", Integer.parseInt(selectedFigureLabel.getText().split(" ")[1]));
                        image = (BufferedImage) resultados.get(0);
                        addLog("Dilator",(long) resultados.get(1), (String) resultados.get(2));
                        updateImage(image);
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error al erosionar la imagen");
                }

            }
        });
    }

    public void addLog(String type,long totalTime, String mode){

        String log = "Tiempo empleado: "+totalTime;

        switch(type){
            case "Eroder":
                if(mode == "P"){
                    logHistory += "[P][Eroder] " + log + "ms\n";
                }else{
                    logHistory += "[S][Eroder] " + log + "ms\n";
                }
                break;
            case "Dilator":
                if(mode == "P"){
                    logHistory += "[P][Dilator] " + log + "ms\n";
                }else{
                    logHistory += "[S][Dilator] " + log + "ms\n";
                }
        }

        this.logArea.setText(logHistory);

    }

    public void updateImage(BufferedImage img) {
        ImageIcon icon = new ImageIcon(getScaledImage(img, 600, 400));
        imageLabel.setIcon(icon);
        imageLabel.setText(null);
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