package Solutions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener
{
    private JTextField display;
    private JButton[] numberButtons;
    private JButton addButton, subtractButton, multiplyButton, divideButton;
    private JButton decimalButton, equalButton, clearButton, signButton, percentButton, modulusButton;
    private double num1 = 0, num2 = 0, result = 0;
    private char operator;

    public Calculator()
    {
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(221, 225, 231));

        // Display setup
        display = new JTextField();
        display.setEditable(false);
        display.setFont(new Font("Helvetica Neue", Font.BOLD, 36));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setBackground(Color.WHITE);
        display.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 4, 10, 10));
        buttonPanel.setBackground(new Color(221, 225, 231));

        // Creating buttons with creme color
        Color buttonColor = new Color(245, 245, 220); // Creme color
        clearButton = createCircularButton("C", buttonColor);
        signButton = createCircularButton("+/-", buttonColor);
        percentButton = createCircularButton("%", buttonColor);
        divideButton = createCircularButton("/", buttonColor);
        modulusButton = createCircularButton("%", buttonColor);
        numberButtons = new JButton[10];

        for (int i = 0; i < 10; i++)
        {
            numberButtons[i] = createCircularButton(String.valueOf(i), Color.WHITE);
        }

        addButton = createCircularButton("+", buttonColor);
        subtractButton = createCircularButton("-", buttonColor);
        multiplyButton = createCircularButton("×", buttonColor);
        decimalButton = createCircularButton(".", Color.WHITE);
        equalButton = createCircularButton("=", buttonColor);

        // Adding buttons in the specified order
        buttonPanel.add(clearButton);
        buttonPanel.add(signButton);
        buttonPanel.add(percentButton);
        buttonPanel.add(divideButton);
        buttonPanel.add(numberButtons[7]);
        buttonPanel.add(numberButtons[8]);
        buttonPanel.add(numberButtons[9]);
        buttonPanel.add(multiplyButton);
        buttonPanel.add(numberButtons[4]);
        buttonPanel.add(numberButtons[5]);
        buttonPanel.add(numberButtons[6]);
        buttonPanel.add(subtractButton);
        buttonPanel.add(numberButtons[1]);
        buttonPanel.add(numberButtons[2]);
        buttonPanel.add(numberButtons[3]);
        buttonPanel.add(addButton);
        buttonPanel.add(modulusButton);
        buttonPanel.add(numberButtons[0]);
        buttonPanel.add(decimalButton);
        buttonPanel.add(equalButton);
        add(buttonPanel, BorderLayout.CENTER);
    }

    private JButton createCircularButton(String text, Color backgroundColor)
    {
        JButton button = new JButton(text)
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                int size = Math.min(getWidth(), getHeight());
                g.setColor(getBackground());
                g.fillOval(0, 0, size, size);
                super.paintComponent(g);
            }

            @Override
            public Dimension getPreferredSize()
            {
                return new Dimension(60, 60); // Size of circular buttons
            }
        };

        button.setFont(new Font("Helvetica Neue", Font.BOLD, 24));
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        for (int i = 0; i < 10; i++)
        {
            if (e.getSource() == numberButtons[i])
            {
                display.setText(display.getText().concat(String.valueOf(i)));
            }
        }

        if (e.getSource() == decimalButton)
        {
            if (!display.getText().contains("."))
            {
                display.setText(display.getText().concat("."));
            }
        }

        if (e.getSource() == clearButton)
        {
            display.setText("");
            num1 = num2 = result = 0;
            operator = ' ';
        }

        if (e.getSource() == signButton)
        {
            if (!display.getText().isEmpty())
            {
                double temp = Double.parseDouble(display.getText());
                temp *= -1;
                display.setText(String.valueOf(temp));
            }
        }

        if (e.getSource() == percentButton)
        {
            if (!display.getText().isEmpty())
            {
                double temp = Double.parseDouble(display.getText());
                temp /= 100;
                display.setText(formatResult(temp));
            }
        }

        if (e.getSource() == addButton || e.getSource() == subtractButton ||
                e.getSource() == multiplyButton || e.getSource() == divideButton ||
                e.getSource() == modulusButton)
        {
            if (!display.getText().isEmpty())
            {
                num1 = Double.parseDouble(display.getText());
                operator = e.getActionCommand().charAt(0);
                display.setText("");
            }
        }

        if (e.getSource() == equalButton)
        {
            if (!display.getText().isEmpty())
            {
                num2 = Double.parseDouble(display.getText());
                switch (operator)
                {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '×':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 != 0)
                        {
                            result = num1 / num2;
                        }
                        else
                        {
                            display.setText("Error");
                            return;
                        }
                        break;
                    case '%':
                        result = num1 % num2;
                        break;
                }
                display.setText(formatResult(result));
                num1 = result;
            }
        }
    }

    private String formatResult(double value)
    {
        if (value % 1 == 0)
        {
            return String.valueOf((int) value);
        }
        else
        {
            return String.format("%.3f", value);
        }
    }

    public static void main(String[] args)
    {
        Calculator calculator = new Calculator();
        calculator.setVisible(true);
    }
}
