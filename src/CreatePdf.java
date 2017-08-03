import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreatePdf implements ActionListener{

    JFrame frame;
    JLabel lab_name,lab_company,lab_address,lab_town,lab_phone,lab_qty,lab_product,lab_price;
    JTextField tf_name,tf_company,tf_address,tf_town,tf_phone,tf_qty,tf_product,tf_price;
    JButton btn_submit,btn_clear;
    String name,company,address,town,phone,qty,product,price,amt_convert,gst_convert,cst_convert,total_convert;


    public CreatePdf(){
        frame=new JFrame("Invoice");

        lab_name=new JLabel("Enter Name:");
        lab_company=new JLabel("Company Name:");
        lab_address=new JLabel("Address:");
        lab_town=new JLabel("Town");
        lab_phone=new JLabel("Mobile/Phone:");
        lab_qty=new JLabel("Quantity Purchased[1]:");
        lab_product=new JLabel("Product Name[1]:");
        lab_price=new JLabel("Product Price[]:");

        tf_name=new JTextField();
        tf_company=new JTextField();
        tf_address=new JTextField();
        tf_town=new JTextField();
        tf_phone=new JTextField();
        tf_qty=new JTextField();
        tf_product=new JTextField();
        tf_price=new JTextField();

        btn_submit=new JButton("Submit");
        btn_clear=new JButton("Clear");

        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        btn_submit.addActionListener(this);


        lab_name.setBounds(10, 10, 170, 20);
        lab_company.setBounds(10, 30, 170, 20);
        lab_address.setBounds(10, 50, 170, 20);
        lab_town.setBounds(10, 70, 170, 20);
        lab_phone.setBounds(10, 90, 170, 20);
        lab_qty.setBounds(10, 110, 170, 20);
        lab_product.setBounds(10, 130, 170, 20);
        lab_price.setBounds(10, 150, 170, 20);


        tf_name.setBounds(170, 10, 300, 20);
        tf_company.setBounds(170, 30, 300, 20);
        tf_address.setBounds(170, 50, 300, 20);
        tf_town.setBounds(170, 70, 300, 20);
        tf_phone.setBounds(170, 90, 300, 20);
        tf_qty.setBounds(170, 110, 300, 20);
        tf_product.setBounds(170, 130, 300, 20);
        tf_price.setBounds(170, 150, 300, 20);

        btn_submit.setBounds(140, 220, 100, 20);
        btn_submit.setBounds(250, 220, 100, 20);

        frame.add(lab_name);
        frame.add(lab_company);
        frame.add(lab_address);
        frame.add(lab_town);
        frame.add(lab_phone);
        frame.add(lab_qty);
        frame.add(lab_product);
        frame.add(lab_price);

        frame.add(tf_name);
        frame.add(tf_company);
        frame.add(tf_address);
        frame.add(tf_town);
        frame.add(tf_phone);
        frame.add(tf_qty);
        frame.add(tf_product);
        frame.add(tf_price);

        frame.add(btn_submit);
        frame.add(btn_clear);

        frame.setSize(500, 300);
        frame.setVisible(true);


    }

    void getUserData(){

        name=tf_name.getText();
        company=tf_company.getText();
        address=tf_address.getText();
        town=tf_town.getText();
        phone=tf_phone.getText();
        qty=tf_qty.getText();
        product=tf_product.getText();
        price=tf_price.getText();

        JOptionPane.showMessageDialog(null,"Data Printed Successfully");

        //total amount
        int qty_convert=Integer.parseInt(qty);

        int price_convert=Integer.parseInt(price);

        int amount=qty_convert*price_convert;

        amt_convert=Integer.toString(amount);

        //total amount with GST

        float gst=(amount*9)/100;
        gst_convert=Float.toString(gst);


        //total amount with CST
        float cst=(amount*9)/100;
        cst_convert=Float.toString(cst);
        float total=amount+gst+cst;
        total_convert=Float.toString(total);

    }

   void getConnection() throws ClassNotFoundException, SQLException {
       Class.forName("com.mysql.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/JDBCToday","root","raj");
        Statement st1 = con1.createStatement();
        boolean z=st1.execute("INSERT INTO invoicepdf (user_name,user_cmpny,user_add,user_town,user_mob,qty,product_name,product_price) VALUES('"+name+"','"+company+"','"+address+"','"+town+"','"+phone+"','"+qty+"','"+product+"','"+price+"')");
        if(z==true){
            System.out.println("Inserted");
        }
        st1.close();
        con1.close();
        //JOptionPane.showMessageDialog(null,"Data are Registered Successfully");
   }

    boolean validateForm(){
        if (tf_name.getText().isEmpty() || tf_company.getText().isEmpty() || tf_address.getText().isEmpty() || tf_town.getText().isEmpty() || tf_phone.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Please enter customer info !");
            return false;
        }else if (tf_qty.getText().isEmpty() || tf_product.getText().isEmpty() || tf_price.getText().isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Please fill one product info at least !");
            return false;
        }else { return true;}
    }

    void gereratePdf() throws FileNotFoundException, DocumentException {

        Font blueFont = FontFactory.getFont(FontFactory.COURIER, 24, Font.BOLD, new CMYKColor(0, 0, 0, 255));
        Font bold_small = FontFactory.getFont(FontFactory.COURIER, 13, Font.BOLD, new CMYKColor(0, 0, 0, 255));
        Font vsmall = FontFactory.getFont(FontFactory.COURIER, 12, Font.NORMAL, new CMYKColor(0, 0, 0, 255));
        Font red= FontFactory.getFont(FontFactory.COURIER, 8, Font.BOLD, new CMYKColor(0, 255, 0, 0));

        Document document=new Document();
        try{
            PdfWriter writer=PdfWriter.getInstance(document,new FileOutputStream("Invoice.pdf"));
            document.open();
            document.add(new Paragraph("Vighnahar Light",blueFont));
            document.add(new Paragraph("Company Slogan Here",bold_small));
            document.add(new Phrase("\n"));
            document.add(new Paragraph("A/P:Rajuri,Tal-Junnar,Dist-Pune",vsmall));
            document.add(new Paragraph("Rajuri(Bajarpeth) 412-411",vsmall));
            document.add(new Paragraph("Mob [9890408042]",vsmall));
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Paragraph("Billing Address",bold_small));
            document.add(new Paragraph(name,vsmall));
            document.add(new Paragraph(company,vsmall));
            document.add(new Paragraph(address,vsmall));
            document.add(new Paragraph(town,vsmall));
            document.add(new Paragraph(phone,vsmall));
            document.add(new Phrase("\n"));

            Image img=Image.getInstance("/home/raj/Desktop/b.jpg");
            img.setAbsolutePosition(400f, 650f);
            document.add(img);


            PdfPTable table=new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingAfter(5f);
            table.setSpacingBefore(5f);

            float[] colWidth={};
            PdfPCell head_qty=new PdfPCell(new Paragraph("Quantity",bold_small));
            head_qty.setPadding(5);
            PdfPCell head_product=new PdfPCell(new Paragraph("Product Name",bold_small));
            head_product.setPadding(5);
            PdfPCell head_price=new PdfPCell(new Paragraph("Unit Price",bold_small));
            head_price.setPadding(5);
            PdfPCell head_amt=new PdfPCell(new Paragraph("Total Amount",bold_small));
            head_amt.setPadding(5);

            PdfPCell head_qty1=new PdfPCell(new Paragraph(qty));
            head_qty1.setFixedHeight(150f);
            PdfPCell head_product1=new PdfPCell(new Paragraph(product));
            PdfPCell head_price1=new PdfPCell(new Paragraph(price));
            PdfPCell head_amt1=new PdfPCell(new Paragraph(amt_convert));
            head_qty1.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);
            head_amt1.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);
            head_price1.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);
            head_product1.setHorizontalAlignment(Paragraph.ALIGN_RIGHT);




            table.addCell(head_qty);
            table.addCell(head_product);
            table.addCell(head_price);
            table.addCell(head_amt);

            table.addCell(head_qty1);
            table.addCell(head_product1);
            table.addCell(head_price1);
            table.addCell(head_amt1);


            document.add(table);

            Paragraph para3 = new Paragraph();
            Paragraph para_gst=new Paragraph();
            para3.setTabSettings(new TabSettings(340f));
            Chunk glue=new Chunk(new VerticalPositionMark());
            para3.add(Chunk.TABBING);
            para3.add(new Chunk("GST(9%)"));
            para3.add(new Chunk(glue));
            para3.add(new Chunk(gst_convert));
            document.add(para3);


            Paragraph para4 = new Paragraph();
            Paragraph para_cst=new Paragraph();
            para4.setTabSettings(new TabSettings(340f));
            Chunk glue1=new Chunk(new VerticalPositionMark());
            para4.add(Chunk.TABBING);
            para4.add(new Chunk("CST(9%)"));
            para4.add(new Chunk(glue1));
            para4.add(new Chunk(cst_convert));
            document.add(para4);


            Paragraph para5 = new Paragraph();
            Paragraph para_total=new Paragraph();
            para5.setTabSettings(new TabSettings(275f));
            Chunk glue2=new Chunk(new VerticalPositionMark());
            para5.add(Chunk.TABBING);
            para5.add(new Chunk("Total Payable Amount "));
            para5.add(new Chunk(glue2));
            para5.add(new Chunk(total_convert));
            document.add(para5);

            Paragraph para6 =new Paragraph("Stamp/Signature:");
            document.add(para6);

            PdfContentByte canvas = writer.getDirectContent();
            CMYKColor magentaColor = new CMYKColor(0.f, 0.f, 0.f, 1.f);
            canvas.setColorStroke(magentaColor);
            canvas.moveTo(450, 295);
//            canvas.lineTo(36, 806);
            canvas.lineTo(560, 295);
//            canvas.lineTo(559, 806);
            canvas.closePathStroke();


            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Phrase("\n"));
            document.add(new Paragraph("Make all cheques payable to Vighnahar Light House ",red));
            document.add(new Paragraph("If you have any questiones concerning this invoice, contact[SMK,9890408042,vighnahar@gmail.com]",red));


            document.close();
            writer.close();


        }catch(DocumentException e){
            e.printStackTrace();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

        //validateForm();
//       if(validateForm()){
//
//       }


        if (validateForm()){
        getUserData();
        try {
            gereratePdf();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        try {
            getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        }else{JOptionPane.showMessageDialog(null,"Please Complete the form..!");}

    }

    public static void main(String[] args) {
        CreatePdf cp=new CreatePdf();
    }
}
