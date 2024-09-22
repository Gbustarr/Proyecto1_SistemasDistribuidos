package utils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Image;

public class WindowMenu extends JFrame {
    
    private JLabel imageLabel;
    private JLabel imageNameLabel;
    private BufferedImage image;
    
    public WindowMenu() {
        // Configurar la ventana
        setTitle("Procesamiento de Imagen");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear un panel para la imagen
        imageLabel = new JLabel("Carga una imagen", JLabel.CENTER);
        imageLabel.setPreferredSize(new Dimension(600, 400));
        add(imageLabel, BorderLayout.CENTER);

        // Crear una etiqueta para mostrar el nombre de la imagen cargada
        imageNameLabel = new JLabel("", JLabel.CENTER);
        add(imageNameLabel, BorderLayout.NORTH);

        // Crear botones
        JButton loadButton = new JButton("Cargar Imagen");
        JButton contaminarImagen = new JButton("Contaminar Imagen");
        JButton erodeButton = new JButton("Erosionar");
        JButton dilateButton = new JButton("Dilatar");

        // Panel para botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(loadButton);
        buttonPanel.add(contaminarImagen);
        buttonPanel.add(erodeButton);
        buttonPanel.add(dilateButton);
        add(buttonPanel, BorderLayout.SOUTH);

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
                        imageNameLabel.setText("Imagen: " + file.getName()+"\nWidth: " + image.getWidth()+"\nHeight: " + image.getHeight());
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Error al cargar la imagen");
                    }
                }
            }
        });

        // Acciones para los botones "Erosionar" y "Dilatar" (no implementadas aún)
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
