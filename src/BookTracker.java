import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

// These get automatically generated as I build/edit the GUI using the Swing UI Design form.
public class BookTracker {
    private JPanel Main;
    private JButton saveButton;
    private JButton clearButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton searchButton;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JTextField txtYear;
    private JTextField txtGenre;
    private JTextField txtPages;
    private JTextField txtSearch;

    // Form Main (), main method template automatically generated using the 'Code' -> 'Generate' option within intellij.
    public static void main(String[] args) {
        JFrame frame = new JFrame("BookTracker");
        frame.setContentPane(new BookTracker().Main);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Declaring DB connection objects.
    Connection con;
    PreparedStatement pst;

    // Action listener for the Save button. I generated this by right-clicking on the desired button within the GUI and
    // selecting Create Action Listener.
    public BookTracker() {
        // calling Connect() method inside this constructor, to give the buttons DB connectivity.
        Connect();

        // CODE BLOCK FOR THE SAVE BUTTON.
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // code below only uses string as the data type because no calculations will be needed for this app.
                String title, author, year, genre, pages;

                title = txtTitle.getText();
                author = txtAuthor.getText();
                year = txtYear.getText();
                genre = txtGenre.getText();
                pages = txtPages.getText();

                try {
                    // No need to write in the BookID because it will be automatically incremented in the DB.
                    // code below will insert the data into the DB Books table, after the user types it and clicks
                    // the Save button.
                    pst = con.prepareStatement("insert into books(Title,Author,Year,Genre,Pages)values(?,?,?,?,?)");
                    pst.setString(1, title);
                    pst.setString(2, author);
                    pst.setString(3, year);
                    pst.setString(4, genre);
                    pst.setString(5, pages);
                    pst.executeUpdate();
                    // line below will display a message if the new book was successfully added.
                    JOptionPane.showMessageDialog(null, title + " was added to your library!");

                    // code below will clear the text fields after the book has been added/saved.
                    txtTitle.setText("");
                    txtAuthor.setText("");
                    txtYear.setText("");
                    txtGenre.setText("");
                    txtPages.setText("");
                    // the code line below will make it so the cursor will go back to the first text field
                    // so a new entry can be started.
                    txtTitle.requestFocus();
                }
                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        // CODE BLOCK FOR THE SEARCH BUTTON.
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String search = txtSearch.getText();
                    // SQL statement below will conduct the DB search, based on the book title input.
                    pst = con.prepareStatement("select Title,Author,Year,Genre,Pages from books where Title = ?");
                    pst.setString(1, search);
                    ResultSet rs = pst.executeQuery();

                    // Code block below says, if the Title given in the search field is correct,
                    // the book information will be displayed.
                    if(rs.next())
                    {
                        String title = rs.getString(1);
                        String author = rs.getString(2);
                        String year = rs.getString(3);
                        String genre = rs.getString(4);
                        String pages = rs.getString(5);

                        // displaying the book details after it's fetched from the DB.
                        txtTitle.setText(title);
                        txtAuthor.setText(author);
                        txtYear.setText(year);
                        txtGenre.setText(genre);
                        txtPages.setText(pages);
                    }
                    else
                    {
                        // if the book title is not found, the code lines below will reset the text fields.
                        // and will display a message letting the user know.
                        txtTitle.setText("");
                        txtAuthor.setText("");
                        txtYear.setText("");
                        txtGenre.setText("");
                        txtPages.setText("");
                        JOptionPane.showMessageDialog(null,"Book title not found! " +
                                "Check your spelling then try again!");
                    }
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });

        //CODE BLOCK FOR THE UPDATE BUTTON
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title, author, year, genre, pages, search;

                title = txtTitle.getText();
                author = txtAuthor.getText();
                year = txtYear.getText();
                genre = txtGenre.getText();
                pages = txtPages.getText();
                search = txtSearch.getText();

                try {
                    // update SQL script will execute when a book is fetched from the DB, changes made, and update button clicked.
                    pst = con.prepareStatement("update books set Title = ?,Author = ?,Year = ?,Genre = ?,Pages = ? where Title = ?");
                    pst.setString(1, title);
                    pst.setString(2, author);
                    pst.setString(3, year);
                    pst.setString(4, genre);
                    pst.setString(5, pages);
                    pst.setString(6, search);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, title + " has been updated!");

                    //the code lines below will reset the text fields.
                    txtTitle.setText("");
                    txtAuthor.setText("");
                    txtYear.setText("");
                    txtGenre.setText("");
                    txtPages.setText("");
                    txtSearch.setText("");
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        // CODE BLOCK FOR THE DELETE BUTTON.
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String title;
                title = txtTitle.getText();

                try {
                    // SQL delete script. Will delete the book that was searched.
                    pst = con.prepareStatement("delete from books where Title = ?");
                    pst.setString(1, title);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null, title + " was deleted from your library!");

                    txtTitle.setText("");
                    txtAuthor.setText("");
                    txtYear.setText("");
                    txtGenre.setText("");
                    txtPages.setText("");
                    txtSearch.setText("");
                }

                catch (SQLException e1)
                {
                    e1.printStackTrace();
                }
            }
        });

        // CODE BLOCK FOR THE CLEAR BUTTON. IT WILL CLEAR THE DATA FIELDS AT ANY POINT WHILE THE APP IS RUNNING.
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtTitle.setText("");
                txtAuthor.setText("");
                txtYear.setText("");
                txtGenre.setText("");
                txtPages.setText("");
                txtSearch.setText("");
            }
        });
    }

    // Code below can be done after I create the DB in the SQL server AND after I add the DB connection JAR file into
    // the project Structure, by going to Files -> Modules -> Dependencies -> and add the jar file.
    // Code below is used to set up the DB connection between my project and the DB I created for it.

    public void Connect()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/my_library", "root", "root");
            // String below will be displayed in the terminal if the DB connection was set up correctly.
            System.out.println("DB Connection established!");
        }
        catch (ClassNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

}
