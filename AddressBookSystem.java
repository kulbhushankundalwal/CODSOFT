import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Contact class
class Contact implements Serializable {
    private String name;
    private String phoneNumber;
    private String emailAddress;

    public Contact(String name, String phoneNumber, String emailAddress) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Phone Number: " + phoneNumber + ", Email Address: " + emailAddress;
    }
}

// AddressBook class
class AddressBook {
    private List<Contact> contacts;

    public AddressBook() {
        contacts = new ArrayList<>();
    }

    public void addContact(Contact contact) {
        contacts.add(contact);
    }

    public void removeContact(Contact contact) {
        contacts.remove(contact);
    }

    public Contact searchContact(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equalsIgnoreCase(name)) {
                return contact;
            }
        }
        return null;
    }

    public List<Contact> getAllContacts() {
        return contacts;
    }

    // Write contacts to a file
    public void writeContactsToFile(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(contacts);
            objectOut.close();
            fileOut.close();
            System.out.println("Contacts written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Read contacts from a file
    public void readContactsFromFile(String filename) {
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            contacts = (ArrayList<Contact>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("Contacts read from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// GUI for the Address Book System using Swing
class AddressBookGUI {
    private AddressBook addressBook;
    private JFrame frame;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JTextArea outputArea;

    public AddressBookGUI(AddressBook addressBook) {
        this.addressBook = addressBook;
        createGUI();
    }

    private void createGUI() {
        frame = new JFrame("Address Book");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();
        JLabel phoneLabel = new JLabel("Phone Number:");
        phoneField = new JTextField();
        JLabel emailLabel = new JLabel("Email Address:");
        emailField = new JTextField();

        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(emailLabel);
        inputPanel.add(emailField);

        JButton addButton = new JButton("Add Contact");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String phone = phoneField.getText();
                String email = emailField.getText();

                if (name.isEmpty() || phone.isEmpty() || email.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    Contact contact = new Contact(name, phone, email);
                    addressBook.addContact(contact);
                    nameField.setText("");
                    phoneField.setText("");
                    emailField.setText("");
                    JOptionPane.showMessageDialog(frame, "Contact added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        JButton displayButton = new JButton("Display Contacts");
        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Contact> contacts = addressBook.getAllContacts();
                StringBuilder sb = new StringBuilder();
                for (Contact contact : contacts) {
                    sb.append(contact).append("\n");
                }
                outputArea.setText(sb.toString());
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);

        outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);
        frame.getContentPane().add(scrollPane, BorderLayout.SOUTH);

        frame.pack();
        frame.setVisible(true);
    }
}

// Main class to run the application
public class AddressBookSystem {
    public static void main(String[] args) {
        AddressBook addressBook = new AddressBook();
        addressBook.readContactsFromFile("contacts.dat"); // Load contacts from a file (if available)

        AddressBookGUI gui = new AddressBookGUI(addressBook);

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                addressBook.writeContactsToFile("contacts.dat"); // Save contacts to a file before exiting
            }
        }));
    }
}
