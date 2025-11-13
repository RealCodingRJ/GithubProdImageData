import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

static class Window extends JFrame {

    public Window() {

        setSize(400, 600);
        setResizable(false);
        try(HttpClient cl = HttpClient.newBuilder().build()) {

            URL nameGithubURL = URI.create("https://www.github.com/RealCodingRJ").toURL();

            URL url = URI.create("https://avatars.githubusercontent.com/u/198405425?v=4").toURL();
            HttpRequest req = HttpRequest.newBuilder(URI.create(url.toString())).GET().build();
            HttpRequest reqGithub = HttpRequest.newBuilder(nameGithubURL.toURI()).GET().build();

            setLayout(new FlowLayout());
            JLabel name = new JLabel(reqGithub.uri().toURL().toString());
            JLabel label = new JLabel();
            label.setSize(new Dimension(1300, 900));
            HttpResponse<Void> res = cl.send(req, HttpResponse.BodyHandlers.discarding());
            getContentPane().setBackground(new Color(5 * 2, 5 * 2, 5 * 2));

            name.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    name.setForeground(Color.white);
                    name.setFont(new Font("sans-serif", Font.BOLD, 17));

                    super.mouseClicked(e);

                }
            });


            BufferedImage img;
            try {
                img = ImageIO.read(res.uri().toURL());

                label.setIcon(new ImageIcon(img));

                getContentPane().add(label);
                getContentPane().add(name);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
}

void main() {

    Window win = new Window();
    win.setVisible(true);

}