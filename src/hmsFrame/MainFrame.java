package hmsFrame;

import hmsModel.*;
import hmsDataService.*;
import hmsService.*;

import java.util.*;
import java.awt.Color;
import java.awt.GridLayout;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.persistence.EntityManagerFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mrkjse
 */
public class MainFrame extends javax.swing.JFrame { 
    
    ArrayList<EntityManagerFactory> emfactoryList = null;
    DefaultDataService defaultController = null;
    HotelDataService hotelController = null;
    RoomDataService roomController = null;
    BookingDataService bookingController = null;
    CustomerDataService customerController = null;
    PaymentDataService paymentController = null;
    MembershipDataService membershipController = null;
    GuestDataService guestController = null;
    
    Hotel hotel = new Hotel();
    ArrayList<Hotel> hotels = new ArrayList<>();
    Room room = new Room();
    ArrayList<Room> rooms = new ArrayList<>();
    Guest guest = new Guest();
    ArrayList<Guest> guests = new ArrayList<>();
    Membership membership = new Membership();
    ArrayList<Membership> memberships = new ArrayList<>();
    Payment payment = new Payment();
    ArrayList<Payment> payments = new ArrayList<>();
    Booking booking = new Booking();
    ArrayList<Booking> bookings = new ArrayList<>();
    Customer customer = new Customer();
    ArrayList<Customer> customers = new ArrayList<>();
    ArrayList<RoomType> roomTypes = new ArrayList<>();
    
    ArrayList<Facility> facilities = new ArrayList<>();
    
    BookingService bookingService = null;
    FinderService finderService = null;
    RoomAllocatorService roomAllocatorService = null;
    
     
    Object hotelColumnHeaders [] = {"HOTEL ID", "HOTEL NAME", "HOTEL TYPE CODE", "CONSTRUCTION YEAR", "COUNTRY", "CITY", "ADDRESS", "CONTACT NUMBER", "EMAIL ADDRESS"};
    Object bookingColumnHeaders [] = {"BOOKING ID", "CUSTOMER ID", "CHECK IN DATE", "CHECK OUT DATE", "CONTACT PERSON", "CONTACT EMAIL", "TOTAL AMOUNT", "CURRENCY CODE", "PAYMENT STATUS"};
    Object roomColumnHeaders [] = {"HOTEL ID", "ROOM ID", "ROOM NUMBER", "ROOM TYPE", "ROOM PRICE", "ROOM PRICE CURRENCY CODE", "ROOM DESCRIPTION"};
    Object guestColumnHeaders [] = {"GUEST ID", "BOOKING ID", "TITLE", "FIRST NAME", "LAST NAME", "BIRTH DATE", "COUNTRY", "CITY", "STREET", "POSTAL CODE", "PHONE NUMBER", "EMAIL ADDRESS"};
    Object customerColumnHeaders [] = {"CUSTOMER ID", "MEMBERSHIP TIER", "MEMBERSHIP CREDITS", "TITLE", "FIRST NAME", "LAST NAME", "BIRTH DATE", "COUNTRY", "CITY", "STREET", "POSTAL CODE", "PHONE NUMBER", "EMAIL ADDRESS"};
    Object membershipColumnHeaders [] = {"MEMBERSHIP TIER CODE", "MEMBERSHIP TIER", "TIER CREDITS", "DISCOUNT", "OTHER REWARDS"};
    Object paymentColumnHeaders [] = {"BOOKING ID", "PAYMENT NUMBER", "PAYMENT DATE", "PAYMENT AMOUNT", "PAYMENT CURRENCY CODE", "PAYMENT METHOD"};

   

    DefaultTableModel hotelTableModel = new DefaultTableModel();
    DefaultTableModel bookingTableModel = new DefaultTableModel();
    DefaultTableModel roomTableModel = new DefaultTableModel();
    DefaultTableModel guestTableModel = new DefaultTableModel();
    DefaultTableModel paymentTableModel = new DefaultTableModel();
    DefaultTableModel customerTableModel = new DefaultTableModel();
    DefaultTableModel membershipTableModel = new DefaultTableModel();

    DefaultComboBoxModel hotelTypeCBModel = new DefaultComboBoxModel();
    DefaultComboBoxModel paymentMethodCBModel = new DefaultComboBoxModel();
    DefaultComboBoxModel roomFacilityCBModel = new DefaultComboBoxModel();
    DefaultComboBoxModel customerCBModel = new DefaultComboBoxModel();
    DefaultComboBoxModel membershipTierCBModel = new DefaultComboBoxModel();
    
    ArrayList<String> htcString = new ArrayList<>();
    ArrayList<String> htc2String = new ArrayList<>();
    ArrayList<String> pmcString = new ArrayList<>();
    ArrayList<String> rfString = new ArrayList<>();
    ArrayList<String> customerString = new ArrayList<>();
    ArrayList<String> customer2String = new ArrayList<>();
    ArrayList<String> membershipString = new ArrayList<>();
    ArrayList<String> membership2String = new ArrayList<>();
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        defaultController = new DefaultDataService();
        
        initComponents();
        
        addListeners();
        
        try
        {
            emfactoryList = defaultController.LogIn();
            hotelController = new  HotelDataService(emfactoryList.get(0));
            bookingController = new BookingDataService(emfactoryList.get(1));
            roomController = new RoomDataService(emfactoryList.get(1));
            guestController = new GuestDataService(emfactoryList.get(1));
            paymentController = new PaymentDataService(emfactoryList.get(1));
            customerController = new CustomerDataService(emfactoryList.get(1));
            membershipController = new MembershipDataService(emfactoryList.get(1)); 
            
            roomAllocatorService = new RoomAllocatorService(emfactoryList.get(1));
            bookingService = new BookingService();
            
            hotelPanel.setVisible(true);
            roomPanel.setVisible(false);
            customerPanel.setVisible(false);
            paymentPanel.setVisible(false);
            bookingPanel.setVisible(false);
            membershipPanel.setVisible(false);
            guestPanel.setVisible(false);
                       
            roomTypes = new ArrayList<>(roomController.getRoomTypes());
            
            facilities = new ArrayList<>(roomController.getRoomFacilities());
            
            refreshDataTableModels();
            
            btnHotels.setBackground(Color.decode("#f18973"));
        
        }
        catch (Exception e)
        {

        }
       
        fillUpComboBoxes();
        
    }
    
    private void addListeners()
    {
        ListSelectionListener hotelTableListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                // call method
                tblHotelValueChanged(evt);
            }
        };
        
        tblHotel.getSelectionModel().addListSelectionListener(hotelTableListener);
        
        ListSelectionListener bookingTableListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                // call method
                tblBookingValueChanged(evt);
            }
        };
        
        tblBooking.getSelectionModel().addListSelectionListener(bookingTableListener);
        
        ListSelectionListener roomTableListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                // call method
                tblRoomValueChanged(evt);
            }
        };
        
        tblRoom.getSelectionModel().addListSelectionListener(roomTableListener);
        
        ListSelectionListener guestTableListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                // call method
                tblGuestValueChanged(evt);
            }
        };
        
        tblGuest.getSelectionModel().addListSelectionListener(guestTableListener);
        
        ListSelectionListener customerTableListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                // call method
                tblCustomerValueChanged(evt);
            }
        };
        
        tblCustomer.getSelectionModel().addListSelectionListener(customerTableListener);

        ListSelectionListener membershipTableListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                // call method
                tblMembershipValueChanged(evt);
            }
        };
        
        tblMembership.getSelectionModel().addListSelectionListener(membershipTableListener);

        ListSelectionListener paymentTableListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent evt) {
                // call method
                tblPaymentValueChanged(evt);
            }
        };
        
        tblPayment.getSelectionModel().addListSelectionListener(paymentTableListener);
        
    }
    
    private void fillUpComboBoxes()
    {
        try
        {

            htcString = new ArrayList<>();
            htc2String = new ArrayList<>();
            pmcString = new ArrayList<>();
            
            rfString = new ArrayList<>();
            customerString = new ArrayList<>();
            membershipString = new ArrayList<>();
            
            htcString.add("ALL-See all");
            pmcString.add("ALL-See all");
            rfString.add("ALL-See all");
            customerString.add("ALL-See all");
            membershipString.add("ALL-See all");
            

            for (HotelTypeCode htc : HotelTypeCode.values())
            {
                htcString.add(htc.code() + '-'+ htc.name());
                htc2String.add(htc.code() + '-'+ htc.name());
            }

            for (PaymentMethodCode pmc : PaymentMethodCode.values())
            {
                pmcString.add(pmc.code() + '-'+ pmc.name());
                
            }


            for (Facility f : facilities)
            {
                String a = f.getFacilityNumber() + "-" + f.getFacilityName();
                rfString.add(a);
               
            }
            
            
            for (Customer c : customers)
            {
                String a = c.getCustomerId() + "-" + c.getFirstName() + " " + c.getLastName();
                customerString.add(a);
            }
            
            for (Membership m : memberships)
            {
                String a = m.getMembershipTierCode() + "-" + m.getMembershipTier();
                membershipString.add(a);
                membership2String.add(a);
            }
            
            hotelTypeCBModel = new DefaultComboBoxModel(htcString.toArray());
            paymentMethodCBModel = new DefaultComboBoxModel(pmcString.toArray());
            roomFacilityCBModel = new DefaultComboBoxModel(rfString.toArray());
            customerCBModel = new DefaultComboBoxModel(customerString.toArray());
            membershipTierCBModel = new DefaultComboBoxModel(membershipString.toArray());
           
            
            hotelTypeCodeComboBox.setModel(hotelTypeCBModel);
            
            DefaultComboBoxModel htc2CBModel = new DefaultComboBoxModel(htc2String.toArray());
            
            hotelTypeCodeComboBox2.setModel(htc2CBModel);
            
            paymentMethodComboBox.setModel(paymentMethodCBModel);
            membershipTierComboBox.setModel(membershipTierCBModel);
            
            DefaultComboBoxModel mtc2CBModel = new DefaultComboBoxModel(membership2String.toArray());
            
            membershipTierComboBox2.setModel(mtc2CBModel);
            
            roomFacilityComboBox.setModel(roomFacilityCBModel);
            
            hotelTypeCodeComboBox.repaint();
            roomFacilityComboBox.repaint();
            hotelTypeCodeComboBox2.repaint();
            paymentMethodComboBox.repaint();
            membershipTierComboBox.repaint();
            membershipTierComboBox2.repaint();
            
        }
        catch (Exception e)
        {
            
        }
    
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        menuPanel = new javax.swing.JPanel();
        menuPanel2 = new javax.swing.JPanel();
        btnRooms = new javax.swing.JButton();
        btnBookings = new javax.swing.JButton();
        btnGuests = new javax.swing.JButton();
        btnPayments = new javax.swing.JButton();
        btnHotels = new javax.swing.JButton();
        btnCustomers = new javax.swing.JButton();
        btnMemberships = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        hotelPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        hotelTypeCodeComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblHotel = new javax.swing.JTable();
        addHotelBtn = new javax.swing.JButton();
        editHotelBtn = new javax.swing.JButton();
        deleteHotelBtn = new javax.swing.JButton();
        hotelName = new javax.swing.JLabel();
        hotelType = new javax.swing.JLabel();
        hotelConstructionYear = new javax.swing.JLabel();
        hotelCountry = new javax.swing.JLabel();
        hotelCity = new javax.swing.JLabel();
        hotelAddress = new javax.swing.JLabel();
        hotelContactNumber = new javax.swing.JLabel();
        hotelEmailAddress = new javax.swing.JLabel();
        textHotelName = new javax.swing.JTextField();
        textHotelConstructionYear = new javax.swing.JTextField();
        textHotelCountry = new javax.swing.JTextField();
        textHotelCity = new javax.swing.JTextField();
        textHotelAddress = new javax.swing.JTextField();
        textHotelContactNumber = new javax.swing.JTextField();
        textHotelEmailAddress = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jLabel62 = new javax.swing.JLabel();
        textHotelId = new javax.swing.JTextField();
        jLabel68 = new javax.swing.JLabel();
        hotelTypeCodeComboBox2 = new javax.swing.JComboBox<>();
        roomPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        roomFacilityComboBox = new javax.swing.JComboBox<>();
        addRoomBtn = new javax.swing.JButton();
        editRoomBtn = new javax.swing.JButton();
        delRoomBtn = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        textRoomNumber = new javax.swing.JTextField();
        textRoomDescription = new javax.swing.JTextField();
        textRoomPrice = new javax.swing.JTextField();
        textRoomHotelId = new javax.swing.JTextField();
        textRoomTypeCode = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        jScrollPane8 = new javax.swing.JScrollPane();
        tblRoom = new javax.swing.JTable();
        jLabel64 = new javax.swing.JLabel();
        textRoomId = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        customerPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        membershipTierComboBox = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        addCustBtn = new javax.swing.JButton();
        editCustomerBtn = new javax.swing.JButton();
        delCustomerBtn = new javax.swing.JButton();
        textCustomerTitle = new javax.swing.JTextField();
        textCustomerFirstName = new javax.swing.JTextField();
        textCustomerLastName = new javax.swing.JTextField();
        textCustomerDob = new javax.swing.JTextField();
        textCustomerCountry = new javax.swing.JTextField();
        textCustomerCity = new javax.swing.JTextField();
        textCustomerStreet = new javax.swing.JTextField();
        textCustomerPostalCode = new javax.swing.JTextField();
        textCustomerPhoneNumber = new javax.swing.JTextField();
        textCustomerEmailAddress = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        textCustomerMembershipCredits = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel65 = new javax.swing.JLabel();
        textCustomerId = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        membershipTierComboBox2 = new javax.swing.JComboBox<>();
        guestPanel = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblGuest = new javax.swing.JTable();
        addGuestBtn = new javax.swing.JButton();
        editGuestBtn = new javax.swing.JButton();
        delGuestBtn = new javax.swing.JButton();
        searchGuestFirstName = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        searchGuestLastName = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        labelGuestBookingId = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        textGuestBookingId = new javax.swing.JTextField();
        textGuestTitle = new javax.swing.JTextField();
        textGuestFirstName = new javax.swing.JTextField();
        textGuestLastName = new javax.swing.JTextField();
        textGuestDob = new javax.swing.JTextField();
        textGuestCountry = new javax.swing.JTextField();
        textGuestCity = new javax.swing.JTextField();
        textGuestStreet = new javax.swing.JTextField();
        textGuestPostalCode = new javax.swing.JTextField();
        textGuestPhoneNumber = new javax.swing.JTextField();
        textGuestEmailAddress = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel63 = new javax.swing.JLabel();
        textGuestId = new javax.swing.JTextField();
        searchGuestBtn = new javax.swing.JButton();
        jLabel69 = new javax.swing.JLabel();
        membershipPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblMembership = new javax.swing.JTable();
        addMembershipBtn = new javax.swing.JButton();
        editMembershipBtn = new javax.swing.JButton();
        delMembershipBtn = new javax.swing.JButton();
        searchMembershipAvailableCredit = new javax.swing.JTextField();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel101 = new javax.swing.JLabel();
        jLabel100 = new javax.swing.JLabel();
        textMembershipTierCode = new javax.swing.JTextField();
        textMembershipTierName = new javax.swing.JTextField();
        textMembershipCredit = new javax.swing.JTextField();
        textMembershipDiscount = new javax.swing.JTextField();
        textMembershipOtherRewards = new javax.swing.JTextField();
        jSeparator7 = new javax.swing.JSeparator();
        searchMemberBtn = new javax.swing.JButton();
        paymentPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblPayment = new javax.swing.JTable();
        addPaymentBtn = new javax.swing.JButton();
        editPaymentBtn = new javax.swing.JButton();
        delPaymentBtn = new javax.swing.JButton();
        searchPaymentFirstName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        searchPaymentLastName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        textPaymentBookingId = new javax.swing.JTextField();
        textPaymentAmount = new javax.swing.JTextField();
        jSeparator6 = new javax.swing.JSeparator();
        jLabel61 = new javax.swing.JLabel();
        textPaymentNumber = new javax.swing.JTextField();
        jLabel67 = new javax.swing.JLabel();
        textPaymentDate = new javax.swing.JTextField();
        searchPaymentBtn = new javax.swing.JButton();
        paymentMethodComboBox = new javax.swing.JComboBox<>();
        jLabel70 = new javax.swing.JLabel();
        bookingPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        addBookingBtn = new javax.swing.JButton();
        editBookingBtn = new javax.swing.JButton();
        delBookingBtn = new javax.swing.JButton();
        searchBookingFirstName = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        searchBookingLastName = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblBooking = new javax.swing.JTable();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        textBookingId = new javax.swing.JTextField();
        textBookingCheckInDate = new javax.swing.JTextField();
        textBookingCheckOutDate = new javax.swing.JTextField();
        textBookingContactPerson = new javax.swing.JTextField();
        textBookingContactEmail = new javax.swing.JTextField();
        textBookingCustomerId = new javax.swing.JTextField();
        textBookingPaymentStatusCode = new javax.swing.JTextField();
        textBookingTotalAmount = new javax.swing.JTextField();
        jLabel59 = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jSeparator8 = new javax.swing.JSeparator();
        createBookingBtn = new javax.swing.JButton();
        searchBookingsBtn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 102, 204));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        menuPanel.setOpaque(false);

        btnRooms.setBackground(Color.decode("#86BBD8"));
        btnRooms.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnRooms.setForeground(new java.awt.Color(0, 51, 51));
        btnRooms.setText("ROOMS");
        btnRooms.setBorderPainted(false);
        btnRooms.setOpaque(false);
        btnRooms.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRoomsActionPerformed(evt);
            }
        });

        btnBookings.setBackground(Color.decode("#86BBD8"));
        btnBookings.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnBookings.setForeground(new java.awt.Color(0, 51, 51));
        btnBookings.setText("BOOKINGS");
        btnBookings.setBorderPainted(false);
        btnBookings.setOpaque(false);
        btnBookings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBookingsActionPerformed(evt);
            }
        });

        btnGuests.setBackground(Color.decode("#86BBD8"));
        btnGuests.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnGuests.setForeground(new java.awt.Color(0, 51, 51));
        btnGuests.setText("GUESTS");
        btnGuests.setBorderPainted(false);
        btnGuests.setOpaque(false);
        btnGuests.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuestsActionPerformed(evt);
            }
        });

        btnPayments.setBackground(Color.decode("#86BBD8"));
        btnPayments.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnPayments.setForeground(new java.awt.Color(0, 51, 51));
        btnPayments.setText("PAYMENTS");
        btnPayments.setBorderPainted(false);
        btnPayments.setOpaque(false);
        btnPayments.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPaymentsActionPerformed(evt);
            }
        });

        btnHotels.setBackground(Color.decode("#86BBD8"));
        btnHotels.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnHotels.setForeground(new java.awt.Color(0, 51, 51));
        btnHotels.setText("HOTELS");
        btnHotels.setBorderPainted(false);
        btnHotels.setOpaque(false);
        btnHotels.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHotelsActionPerformed(evt);
            }
        });

        btnCustomers.setBackground(Color.decode("#86BBD8"));
        btnCustomers.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnCustomers.setForeground(new java.awt.Color(0, 51, 51));
        btnCustomers.setText("CUSTOMERS");
        btnCustomers.setBorderPainted(false);
        btnCustomers.setOpaque(false);
        btnCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCustomersActionPerformed(evt);
            }
        });

        btnMemberships.setBackground(Color.decode("#86BBD8"));
        btnMemberships.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        btnMemberships.setForeground(new java.awt.Color(0, 51, 51));
        btnMemberships.setText("MEMBERSHIPS");
        btnMemberships.setBorderPainted(false);
        btnMemberships.setOpaque(false);
        btnMemberships.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMembershipsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanel2Layout = new javax.swing.GroupLayout(menuPanel2);
        menuPanel2.setLayout(menuPanel2Layout);
        menuPanel2Layout.setHorizontalGroup(
            menuPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHotels, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuests, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnMemberships)
                    .addComponent(btnPayments, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBookings, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        menuPanel2Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btnBookings, btnCustomers, btnGuests, btnHotels, btnMemberships, btnPayments, btnRooms});

        menuPanel2Layout.setVerticalGroup(
            menuPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnHotels, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnRooms, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnCustomers, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnGuests, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnMemberships, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnPayments, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55)
                .addComponent(btnBookings, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(76, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, menuPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(menuPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(183, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = 85;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 69, 13, 0);
        getContentPane().add(menuPanel, gridBagConstraints);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("HOTEL MANAGEMENT SYSTEM");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipady = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(80, 69, 0, 0);
        getContentPane().add(jLabel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 1308;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(11, 69, 0, 0);
        getContentPane().add(jSeparator1, gridBagConstraints);

        jLabel2.setText("Search hotel by type");

        hotelTypeCodeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        hotelTypeCodeComboBox.setDoubleBuffered(true);
        hotelTypeCodeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotelTypeCodeComboBoxActionPerformed(evt);
            }
        });

        tblHotel.setModel(hotelTableModel);
        jScrollPane1.setViewportView(tblHotel);

        addHotelBtn.setText("Add");
        addHotelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addHotelBtnActionPerformed(evt);
            }
        });

        editHotelBtn.setText("Edit");
        editHotelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editHotelBtnActionPerformed(evt);
            }
        });

        deleteHotelBtn.setText("Delete");
        deleteHotelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteHotelBtnActionPerformed(evt);
            }
        });

        hotelName.setText("Hotel Name");

        hotelType.setText("Hotel Type");

        hotelConstructionYear.setText("Construction Year");

        hotelCountry.setText("Country");

        hotelCity.setText("City");

        hotelAddress.setText("Address");

        hotelContactNumber.setText("Contact Number");

        hotelEmailAddress.setText("Email Address");

        jLabel62.setText("Hotel Id");

        textHotelId.setEnabled(false);

        jLabel68.setText("must be in yyyy");

        hotelTypeCodeComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        hotelTypeCodeComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hotelTypeCodeComboBox2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout hotelPanelLayout = new javax.swing.GroupLayout(hotelPanel);
        hotelPanel.setLayout(hotelPanelLayout);
        hotelPanelLayout.setHorizontalGroup(
            hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, hotelPanelLayout.createSequentialGroup()
                .addContainerGap(136, Short.MAX_VALUE)
                .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(hotelPanelLayout.createSequentialGroup()
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hotelName)
                            .addComponent(hotelType)
                            .addComponent(hotelConstructionYear)
                            .addComponent(hotelCountry)
                            .addComponent(jLabel62))
                        .addGap(96, 96, 96)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textHotelName)
                            .addComponent(textHotelConstructionYear)
                            .addComponent(textHotelCountry)
                            .addComponent(textHotelId, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                            .addComponent(hotelTypeCodeComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel68)
                        .addGap(79, 79, 79)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hotelCity)
                            .addComponent(hotelAddress)
                            .addComponent(hotelContactNumber)
                            .addComponent(hotelEmailAddress))
                        .addGap(96, 96, 96)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textHotelCity)
                            .addComponent(textHotelAddress)
                            .addComponent(textHotelContactNumber)
                            .addComponent(textHotelEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(hotelPanelLayout.createSequentialGroup()
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(75, 75, 75)
                            .addComponent(hotelTypeCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(hotelPanelLayout.createSequentialGroup()
                            .addGap(480, 480, 480)
                            .addComponent(addHotelBtn)
                            .addGap(18, 18, 18)
                            .addComponent(editHotelBtn)
                            .addGap(18, 18, 18)
                            .addComponent(deleteHotelBtn))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 977, Short.MAX_VALUE)
                        .addComponent(jSeparator4)))
                .addGap(76, 76, 76))
        );

        hotelPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addHotelBtn, deleteHotelBtn, editHotelBtn});

        hotelPanelLayout.setVerticalGroup(
            hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hotelPanelLayout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(hotelPanelLayout.createSequentialGroup()
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel62)
                            .addComponent(textHotelId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hotelName)
                            .addComponent(textHotelName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hotelType)
                            .addComponent(hotelTypeCodeComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hotelConstructionYear)
                            .addComponent(textHotelConstructionYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel68))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hotelCountry)
                            .addComponent(textHotelCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(hotelPanelLayout.createSequentialGroup()
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hotelCity)
                            .addComponent(textHotelCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hotelAddress)
                            .addComponent(textHotelAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(hotelContactNumber)
                            .addComponent(textHotelContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(hotelEmailAddress)
                            .addComponent(textHotelEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(81, 81, 81)
                .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addHotelBtn)
                    .addComponent(editHotelBtn)
                    .addComponent(deleteHotelBtn))
                .addGap(18, 18, 18)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(hotelPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(hotelPanelLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(hotelTypeCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 324, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(hotelPanel, gridBagConstraints);

        jLabel3.setText("Search room by facility");

        roomFacilityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        roomFacilityComboBox.setDoubleBuffered(true);
        roomFacilityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roomFacilityComboBoxActionPerformed(evt);
            }
        });

        addRoomBtn.setText("Add");
        addRoomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRoomBtnActionPerformed(evt);
            }
        });

        editRoomBtn.setText("Edit");
        editRoomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editRoomBtnActionPerformed(evt);
            }
        });

        delRoomBtn.setText("Delete");
        delRoomBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delRoomBtnActionPerformed(evt);
            }
        });

        jLabel37.setText("Room Number");

        jLabel38.setText("Room Description");

        jLabel39.setText("Room Price");

        jLabel40.setText("Hotel Id");

        jLabel41.setText("Room Type Code");

        jLabel42.setText("AUD");

        tblRoom.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(tblRoom);

        jLabel64.setText("Room Id");

        textRoomId.setEnabled(false);

        jLabel71.setText("Room Facilities");

        javax.swing.GroupLayout roomPanelLayout = new javax.swing.GroupLayout(roomPanel);
        roomPanel.setLayout(roomPanelLayout);
        roomPanelLayout.setHorizontalGroup(
            roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomPanelLayout.createSequentialGroup()
                .addGap(94, 94, 94)
                .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roomPanelLayout.createSequentialGroup()
                        .addComponent(jLabel71)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(roomPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(addRoomBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(editRoomBtn)
                        .addGap(18, 18, 18)
                        .addComponent(delRoomBtn)
                        .addGap(173, 173, 173))
                    .addGroup(roomPanelLayout.createSequentialGroup()
                        .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(roomPanelLayout.createSequentialGroup()
                                .addGap(149, 149, 149)
                                .addComponent(roomFacilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(roomPanelLayout.createSequentialGroup()
                                .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(roomPanelLayout.createSequentialGroup()
                                        .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel37)
                                            .addComponent(jLabel38))
                                        .addGap(18, 18, 18)
                                        .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(textRoomDescription)
                                            .addComponent(textRoomNumber)
                                            .addComponent(textRoomId, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel64)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 419, Short.MAX_VALUE)
                                .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel41)
                                    .addComponent(jLabel39))
                                .addGap(18, 18, 18)
                                .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textRoomTypeCode, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                    .addComponent(textRoomHotelId)
                                    .addGroup(roomPanelLayout.createSequentialGroup()
                                        .addComponent(textRoomPrice)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel42))))
                            .addComponent(jSeparator5)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 975, Short.MAX_VALUE))
                        .addContainerGap(222, Short.MAX_VALUE))))
        );

        roomPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addRoomBtn, delRoomBtn, editRoomBtn});

        roomPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textRoomDescription, textRoomHotelId, textRoomNumber, textRoomPrice, textRoomTypeCode});

        roomPanelLayout.setVerticalGroup(
            roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roomPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roomPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(roomPanelLayout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel41))
                            .addGroup(roomPanelLayout.createSequentialGroup()
                                .addComponent(textRoomHotelId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textRoomTypeCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(33, 33, 33)
                        .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel39)
                            .addComponent(textRoomPrice, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel42)))
                    .addGroup(roomPanelLayout.createSequentialGroup()
                        .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel64)
                            .addComponent(textRoomId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel37)
                            .addComponent(textRoomNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel38)
                            .addComponent(textRoomDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(roomPanelLayout.createSequentialGroup()
                        .addGap(104, 104, 104)
                        .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(delRoomBtn)
                            .addComponent(editRoomBtn)
                            .addComponent(addRoomBtn))
                        .addGap(28, 28, 28))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roomPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel71)
                        .addGap(125, 125, 125)))
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addGroup(roomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roomFacilityComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(roomPanel, gridBagConstraints);

        customerPanel.setEnabled(false);

        jLabel4.setText("Search customer by membership tier");

        membershipTierComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        membershipTierComboBox.setDoubleBuffered(true);
        membershipTierComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                membershipTierComboBoxActionPerformed(evt);
            }
        });

        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(tblCustomer);

        addCustBtn.setText("Add");
        addCustBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addCustBtnActionPerformed(evt);
            }
        });

        editCustomerBtn.setText("Edit");
        editCustomerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editCustomerBtnActionPerformed(evt);
            }
        });

        delCustomerBtn.setText("Delete");
        delCustomerBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delCustomerBtnActionPerformed(evt);
            }
        });

        jLabel15.setText("Membership Tier Code");

        jLabel26.setText("Title");

        jLabel27.setText("First Name");

        jLabel28.setText("Last Name");

        jLabel29.setText("Birth Date");

        jLabel30.setText("Country");

        jLabel31.setText("City");

        jLabel32.setText("Street");

        jLabel33.setText("Postal Code");

        jLabel34.setText("Phone Number");

        jLabel35.setText("Email Address");

        jLabel36.setText("Membership Credits");

        jLabel65.setText("Customer Id");

        textCustomerId.setEnabled(false);

        jLabel66.setText("in yyyy-mm-dd");

        membershipTierComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout customerPanelLayout = new javax.swing.GroupLayout(customerPanel);
        customerPanel.setLayout(customerPanelLayout);
        customerPanelLayout.setHorizontalGroup(
            customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerPanelLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel65)
                    .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(customerPanelLayout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(41, 41, 41)
                            .addComponent(membershipTierComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 1061, Short.MAX_VALUE)
                        .addComponent(jSeparator3))
                    .addGroup(customerPanelLayout.createSequentialGroup()
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerPanelLayout.createSequentialGroup()
                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel36)
                                    .addComponent(jLabel26)
                                    .addComponent(jLabel27)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel29))
                                .addGap(37, 37, 37))
                            .addGroup(customerPanelLayout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addGap(21, 21, 21)))
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textCustomerMembershipCredits, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(textCustomerTitle, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(textCustomerFirstName, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(textCustomerLastName)
                            .addComponent(textCustomerDob)
                            .addComponent(textCustomerId)
                            .addComponent(membershipTierComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(customerPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel66))
                            .addGroup(customerPanelLayout.createSequentialGroup()
                                .addGap(292, 292, 292)
                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerPanelLayout.createSequentialGroup()
                                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(customerPanelLayout.createSequentialGroup()
                                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel33)
                                                    .addComponent(jLabel32))
                                                .addGap(35, 35, 35))
                                            .addGroup(customerPanelLayout.createSequentialGroup()
                                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel35)
                                                    .addComponent(jLabel34))
                                                .addGap(18, 18, 18)))
                                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(textCustomerPhoneNumber)
                                                    .addComponent(textCustomerEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, customerPanelLayout.createSequentialGroup()
                                                    .addComponent(textCustomerPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(2, 2, 2)))
                                            .addGroup(customerPanelLayout.createSequentialGroup()
                                                .addComponent(textCustomerStreet, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(2, 2, 2))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerPanelLayout.createSequentialGroup()
                                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel31)
                                            .addComponent(jLabel30))
                                        .addGap(60, 60, 60)
                                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(textCustomerCity, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(textCustomerCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerPanelLayout.createSequentialGroup()
                                        .addComponent(addCustBtn)
                                        .addGap(18, 18, 18)
                                        .addComponent(editCustomerBtn)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(delCustomerBtn)))))))
                .addContainerGap(74, Short.MAX_VALUE))
        );

        customerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addCustBtn, delCustomerBtn, editCustomerBtn});

        customerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textCustomerCity, textCustomerCountry, textCustomerEmailAddress, textCustomerPhoneNumber, textCustomerPostalCode, textCustomerStreet});

        customerPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textCustomerDob, textCustomerFirstName, textCustomerLastName, textCustomerMembershipCredits, textCustomerTitle});

        customerPanelLayout.setVerticalGroup(
            customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(customerPanelLayout.createSequentialGroup()
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerPanelLayout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textCustomerCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, customerPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel65)
                            .addComponent(textCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(customerPanelLayout.createSequentialGroup()
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textCustomerCity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addGap(18, 18, 18)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textCustomerStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addGroup(customerPanelLayout.createSequentialGroup()
                                .addComponent(textCustomerPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(textCustomerPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(textCustomerEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel35)))))
                    .addGroup(customerPanelLayout.createSequentialGroup()
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(membershipTierComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel36)
                            .addComponent(textCustomerMembershipCredits, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textCustomerTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textCustomerFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addGap(18, 18, 18)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textCustomerLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel28))
                        .addGap(18, 18, 18)
                        .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textCustomerDob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(jLabel66))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addCustBtn)
                    .addComponent(editCustomerBtn)
                    .addComponent(delCustomerBtn))
                .addGap(31, 31, 31)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(customerPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(membershipTierComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(115, Short.MAX_VALUE))
        );

        jLabel4.getAccessibleContext().setAccessibleName("Customer");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(customerPanel, gridBagConstraints);

        jLabel5.setText("Search guest by name");

        tblGuest.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(tblGuest);

        addGuestBtn.setText("Add");
        addGuestBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addGuestBtnActionPerformed(evt);
            }
        });

        editGuestBtn.setText("Edit");
        editGuestBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editGuestBtnActionPerformed(evt);
            }
        });

        delGuestBtn.setText("Delete");
        delGuestBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delGuestBtnActionPerformed(evt);
            }
        });

        jLabel13.setText("First Name");

        jLabel14.setText("Last Name");

        labelGuestBookingId.setText("Booking Id");

        jLabel16.setText("Title");

        jLabel17.setText("First Name");

        jLabel18.setText("Last Name");

        jLabel19.setText("Birth Date");

        jLabel20.setText("Country");

        jLabel21.setText("City");

        jLabel22.setText("Street");

        jLabel23.setText("Postal Code");

        jLabel24.setText("Phone Number");

        jLabel25.setText("Email Address");

        textGuestBookingId.setEnabled(false);
        textGuestBookingId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textGuestBookingIdActionPerformed(evt);
            }
        });

        jLabel63.setText("Guest Id");

        textGuestId.setEnabled(false);

        searchGuestBtn.setText("Search");
        searchGuestBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchGuestBtnActionPerformed(evt);
            }
        });

        jLabel69.setText("in yyyy-mm-dd");

        javax.swing.GroupLayout guestPanelLayout = new javax.swing.GroupLayout(guestPanel);
        guestPanel.setLayout(guestPanelLayout);
        guestPanelLayout.setHorizontalGroup(
            guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guestPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guestPanelLayout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textGuestCountry, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guestPanelLayout.createSequentialGroup()
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23)
                            .addComponent(jLabel22)
                            .addComponent(jLabel25, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(5, 5, Short.MAX_VALUE)
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textGuestStreet)
                            .addComponent(textGuestPhoneNumber)
                            .addComponent(textGuestEmailAddress)
                            .addComponent(textGuestPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textGuestCity, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(guestPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(addGuestBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editGuestBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(delGuestBtn)))
                .addGap(302, 302, 302))
            .addGroup(guestPanelLayout.createSequentialGroup()
                .addContainerGap(104, Short.MAX_VALUE)
                .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(guestPanelLayout.createSequentialGroup()
                        .addComponent(searchGuestBtn)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(guestPanelLayout.createSequentialGroup()
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2)
                            .addGroup(guestPanelLayout.createSequentialGroup()
                                .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(guestPanelLayout.createSequentialGroup()
                                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel5)
                                            .addGroup(guestPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addGap(18, 18, 18)
                                                .addComponent(searchGuestFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guestPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel17)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textGuestFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(guestPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel18)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(textGuestLastName))
                                            .addGroup(guestPanelLayout.createSequentialGroup()
                                                .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(labelGuestBookingId)
                                                    .addComponent(jLabel63)
                                                    .addComponent(jLabel16))
                                                .addGap(18, 18, Short.MAX_VALUE)
                                                .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(textGuestBookingId)
                                                    .addComponent(textGuestTitle, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                                                    .addComponent(textGuestId, javax.swing.GroupLayout.Alignment.TRAILING)))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, guestPanelLayout.createSequentialGroup()
                                                .addComponent(jLabel19)
                                                .addGap(29, 29, 29)
                                                .addComponent(textGuestDob)))
                                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(guestPanelLayout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel14)
                                                .addGap(18, 18, 18)
                                                .addComponent(searchGuestLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(guestPanelLayout.createSequentialGroup()
                                                .addGap(21, 21, 21)
                                                .addComponent(jLabel69))))
                                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1010, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(78, 78, 78))))
        );

        guestPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addGuestBtn, delGuestBtn, editGuestBtn});

        guestPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textGuestBookingId, textGuestCity, textGuestCountry, textGuestDob, textGuestEmailAddress, textGuestFirstName, textGuestId, textGuestLastName, textGuestPhoneNumber, textGuestPostalCode, textGuestStreet, textGuestTitle});

        guestPanelLayout.setVerticalGroup(
            guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(guestPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(guestPanelLayout.createSequentialGroup()
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelGuestBookingId)
                            .addComponent(textGuestBookingId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel63)
                            .addComponent(textGuestId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(guestPanelLayout.createSequentialGroup()
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(textGuestCountry, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21)
                            .addComponent(textGuestCity, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(guestPanelLayout.createSequentialGroup()
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(textGuestTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textGuestFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addGap(18, 18, 18)
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel18)
                            .addComponent(textGuestLastName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel19)
                            .addComponent(textGuestDob, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel69)))
                    .addComponent(jLabel22)
                    .addGroup(guestPanelLayout.createSequentialGroup()
                        .addComponent(textGuestStreet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textGuestPostalCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textGuestPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel24))
                        .addGap(18, 18, 18)
                        .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textGuestEmailAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 113, Short.MAX_VALUE)
                .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addGuestBtn)
                    .addComponent(editGuestBtn)
                    .addComponent(delGuestBtn))
                .addGap(36, 36, 36)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(guestPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchGuestFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel14)
                    .addComponent(searchGuestLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchGuestBtn)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(guestPanel, gridBagConstraints);

        jLabel6.setText("Search member by available credits");

        tblMembership.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tblMembership);

        addMembershipBtn.setText("Add");
        addMembershipBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMembershipBtnActionPerformed(evt);
            }
        });

        editMembershipBtn.setText("Edit");
        editMembershipBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editMembershipBtnActionPerformed(evt);
            }
        });

        delMembershipBtn.setText("Delete");
        delMembershipBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delMembershipBtnActionPerformed(evt);
            }
        });

        jLabel43.setText("Tier Code");

        jLabel44.setText("Tier Name");

        jLabel45.setText("Tier Min. Credit");

        jLabel101.setText("Discount");

        jLabel100.setText("Other Rewards");

        searchMemberBtn.setText("Search");
        searchMemberBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMemberBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout membershipPanelLayout = new javax.swing.GroupLayout(membershipPanel);
        membershipPanel.setLayout(membershipPanelLayout);
        membershipPanelLayout.setHorizontalGroup(
            membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, membershipPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addMembershipBtn)
                .addGap(27, 27, 27)
                .addComponent(editMembershipBtn)
                .addGap(18, 18, 18)
                .addComponent(delMembershipBtn)
                .addGap(130, 130, 130))
            .addGroup(membershipPanelLayout.createSequentialGroup()
                .addGap(131, 131, 131)
                .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(membershipPanelLayout.createSequentialGroup()
                        .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel44)
                            .addComponent(jLabel45)
                            .addComponent(jLabel43))
                        .addGap(69, 69, 69)
                        .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textMembershipCredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textMembershipTierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textMembershipTierCode, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(234, 234, 234)
                        .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel100)
                            .addComponent(jLabel101))
                        .addGap(62, 62, 62)
                        .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(textMembershipDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textMembershipOtherRewards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(membershipPanelLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(42, 42, 42)
                        .addComponent(searchMembershipAvailableCredit, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(searchMemberBtn))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 994, Short.MAX_VALUE)
                    .addComponent(jSeparator7))
                .addContainerGap(67, Short.MAX_VALUE))
        );

        membershipPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addMembershipBtn, delMembershipBtn, editMembershipBtn});

        membershipPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textMembershipCredit, textMembershipDiscount, textMembershipOtherRewards, textMembershipTierCode, textMembershipTierName});

        membershipPanelLayout.setVerticalGroup(
            membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(membershipPanelLayout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel43)
                    .addComponent(jLabel101)
                    .addComponent(textMembershipTierCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textMembershipDiscount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44)
                    .addComponent(jLabel100)
                    .addComponent(textMembershipTierName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textMembershipOtherRewards, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45)
                    .addComponent(textMembershipCredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addMembershipBtn)
                    .addComponent(editMembershipBtn)
                    .addComponent(delMembershipBtn))
                .addGap(17, 17, 17)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(membershipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchMembershipAvailableCredit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchMemberBtn))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 396, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(membershipPanel, gridBagConstraints);

        jLabel7.setText("Search payments by customer name");

        tblPayment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane6.setViewportView(tblPayment);

        addPaymentBtn.setText("Add");
        addPaymentBtn.setToolTipText("");
        addPaymentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addPaymentBtnActionPerformed(evt);
            }
        });

        editPaymentBtn.setText("Edit");
        editPaymentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPaymentBtnActionPerformed(evt);
            }
        });

        delPaymentBtn.setText("Delete");
        delPaymentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delPaymentBtnActionPerformed(evt);
            }
        });

        jLabel9.setText("First Name");

        jLabel10.setText("Last Name");

        jLabel46.setText("Booking Id");

        jLabel47.setText("Payment Method Code");

        jLabel48.setText("Payment Amount");

        jLabel49.setText("AUD");

        jLabel61.setText("Payment Number");

        textPaymentNumber.setEnabled(false);

        jLabel67.setText("Payment Date");

        textPaymentDate.setEnabled(false);

        searchPaymentBtn.setText("Search");
        searchPaymentBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchPaymentBtnActionPerformed(evt);
            }
        });

        paymentMethodComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel70.setText("Note: the current date will always be used for commit");

        javax.swing.GroupLayout paymentPanelLayout = new javax.swing.GroupLayout(paymentPanel);
        paymentPanel.setLayout(paymentPanelLayout);
        paymentPanelLayout.setHorizontalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(149, 149, 149)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61)
                    .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane6)
                        .addComponent(jSeparator6)
                        .addGroup(paymentPanelLayout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(paymentPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel9)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(searchPaymentFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel10)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(searchPaymentLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jLabel7)
                                .addComponent(searchPaymentBtn)))
                        .addGroup(paymentPanelLayout.createSequentialGroup()
                            .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel46)
                                .addComponent(jLabel47)
                                .addComponent(jLabel48))
                            .addGap(69, 69, 69)
                            .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(paymentPanelLayout.createSequentialGroup()
                                    .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(paymentMethodComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(textPaymentAmount, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addGap(18, 18, 18)
                                    .addComponent(jLabel49))
                                .addGroup(paymentPanelLayout.createSequentialGroup()
                                    .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(textPaymentNumber, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(textPaymentBookingId, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE))
                                    .addGap(184, 184, 184)
                                    .addComponent(jLabel67)
                                    .addGap(29, 29, 29)
                                    .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(textPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel70)))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paymentPanelLayout.createSequentialGroup()
                            .addComponent(addPaymentBtn)
                            .addGap(18, 18, 18)
                            .addComponent(editPaymentBtn)
                            .addGap(18, 18, 18)
                            .addComponent(delPaymentBtn)
                            .addGap(148, 148, 148))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        paymentPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addPaymentBtn, delPaymentBtn, editPaymentBtn});

        paymentPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textPaymentAmount, textPaymentBookingId});

        paymentPanelLayout.setVerticalGroup(
            paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paymentPanelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(textPaymentBookingId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67)
                    .addComponent(textPaymentDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel61)
                            .addComponent(textPaymentNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(jLabel70)))
                .addGap(24, 24, 24)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel47)
                    .addComponent(paymentMethodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel48)
                    .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(textPaymentAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel49)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addPaymentBtn)
                    .addComponent(editPaymentBtn)
                    .addComponent(delPaymentBtn))
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(searchPaymentFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(jLabel9))
                    .addGroup(paymentPanelLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(paymentPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(searchPaymentLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(searchPaymentBtn)
                .addGap(16, 16, 16)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(paymentPanel, gridBagConstraints);

        jLabel8.setText("Search bookings by customer");

        addBookingBtn.setText("Add");
        addBookingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBookingBtnActionPerformed(evt);
            }
        });

        editBookingBtn.setText("Edit");
        editBookingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBookingBtnActionPerformed(evt);
            }
        });

        delBookingBtn.setText("Delete");
        delBookingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delBookingBtnActionPerformed(evt);
            }
        });

        jLabel11.setText("First Name");

        jLabel12.setText("Last Name");

        tblBooking.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane7.setViewportView(tblBooking);

        jLabel50.setText("Booking Id");

        jLabel51.setText("Check In Date");

        jLabel52.setText("Check Out Date");

        jLabel53.setText("Contact Person");

        jLabel54.setText("Contact Email");

        jLabel55.setText("Customer Id");

        jLabel56.setText("Payment Status Code");

        jLabel57.setText("Total Amount");

        jLabel58.setText("AUD");

        textBookingId.setEnabled(false);

        jLabel59.setText("must be in yyyy-mm-dd");

        jLabel60.setText("must be in yyyy-mm-dd");

        createBookingBtn.setText("CREATE A BOOKING");
        createBookingBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createBookingBtnActionPerformed(evt);
            }
        });

        searchBookingsBtn.setText("Search");
        searchBookingsBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchBookingsBtnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bookingPanelLayout = new javax.swing.GroupLayout(bookingPanel);
        bookingPanel.setLayout(bookingPanelLayout);
        bookingPanelLayout.setHorizontalGroup(
            bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(addBookingBtn)
                .addGap(18, 18, 18)
                .addComponent(editBookingBtn)
                .addGap(18, 18, 18)
                .addComponent(delBookingBtn)
                .addGap(96, 96, 96))
            .addGroup(bookingPanelLayout.createSequentialGroup()
                .addGap(124, 124, 124)
                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 1003, Short.MAX_VALUE)
                    .addGroup(bookingPanelLayout.createSequentialGroup()
                        .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bookingPanelLayout.createSequentialGroup()
                                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel50)
                                    .addComponent(jLabel51))
                                .addGap(58, 58, 58)
                                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textBookingId, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(bookingPanelLayout.createSequentialGroup()
                                        .addComponent(textBookingCheckInDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel59))))
                            .addGroup(bookingPanelLayout.createSequentialGroup()
                                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel52)
                                    .addComponent(jLabel53)
                                    .addComponent(jLabel54))
                                .addGap(49, 49, 49)
                                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textBookingContactEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textBookingContactPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(bookingPanelLayout.createSequentialGroup()
                                        .addComponent(textBookingCheckOutDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel60)))))
                        .addGap(149, 149, 149)
                        .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(bookingPanelLayout.createSequentialGroup()
                                .addComponent(jLabel57)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textBookingTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingPanelLayout.createSequentialGroup()
                                .addComponent(jLabel56)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(textBookingPaymentStatusCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bookingPanelLayout.createSequentialGroup()
                                .addComponent(jLabel55)
                                .addGap(83, 83, 83)
                                .addComponent(textBookingCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel58))
                    .addComponent(jSeparator8)
                    .addGroup(bookingPanelLayout.createSequentialGroup()
                        .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bookingPanelLayout.createSequentialGroup()
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(searchBookingLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, bookingPanelLayout.createSequentialGroup()
                                .addComponent(jLabel11)
                                .addGap(122, 122, 122)
                                .addComponent(searchBookingFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addComponent(searchBookingsBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createBookingBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        bookingPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {addBookingBtn, delBookingBtn, editBookingBtn});

        bookingPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {textBookingCheckInDate, textBookingCheckOutDate, textBookingContactEmail, textBookingContactPerson, textBookingCustomerId, textBookingId, textBookingPaymentStatusCode, textBookingTotalAmount});

        bookingPanelLayout.setVerticalGroup(
            bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bookingPanelLayout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel50)
                    .addComponent(jLabel55)
                    .addComponent(textBookingCustomerId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textBookingId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingPanelLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel51)
                            .addComponent(jLabel56)
                            .addComponent(textBookingCheckInDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel59)))
                    .addGroup(bookingPanelLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(textBookingPaymentStatusCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(31, 31, 31)
                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel57)
                        .addComponent(jLabel58)
                        .addComponent(textBookingTotalAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel52)
                        .addComponent(textBookingCheckOutDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel60)))
                .addGap(23, 23, 23)
                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel53)
                    .addComponent(textBookingContactPerson, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel54)
                    .addComponent(textBookingContactEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delBookingBtn)
                    .addComponent(editBookingBtn)
                    .addComponent(addBookingBtn))
                .addGap(23, 23, 23)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(bookingPanelLayout.createSequentialGroup()
                        .addGroup(bookingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(searchBookingFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(createBookingBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12))
                    .addGroup(bookingPanelLayout.createSequentialGroup()
                        .addComponent(searchBookingsBtn)
                        .addGap(1, 1, 1)
                        .addComponent(searchBookingLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(92, 92, 92))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        getContentPane().add(bookingPanel, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPaymentsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPaymentsActionPerformed
        // TODO add your handling code here:
        btnPayments.setBackground(Color.decode("#f18973"));
        //btnHotels.setForeground(Color.decode("#86BBD8"));

        btnBookings.setBackground(Color.decode("#86BBD8"));

        btnCustomers.setBackground(Color.decode("#86BBD8"));

        btnGuests.setBackground(Color.decode("#86BBD8"));

        btnMemberships.setBackground(Color.decode("#86BBD8"));

        btnHotels.setBackground(Color.decode("#86BBD8"));

        btnRooms.setBackground(Color.decode("#86BBD8"));
        
        refreshDataTableModels();
        
        hotelPanel.setVisible(false);
        roomPanel.setVisible(false);
        customerPanel.setVisible(false);
        paymentPanel.setVisible(true);
        bookingPanel.setVisible(false);
        membershipPanel.setVisible(false);
        guestPanel.setVisible(false);
        paymentPanel.setVisible(true);
        this.repaint();
    }//GEN-LAST:event_btnPaymentsActionPerformed

    private void btnHotelsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHotelsActionPerformed
        // TODO add your handling code here:
        btnHotels.setBackground(Color.decode("#f18973"));
        //btnHotels.setForeground(Color.decode("#86BBD8"));

        btnBookings.setBackground(Color.decode("#86BBD8"));

        btnCustomers.setBackground(Color.decode("#86BBD8"));

        btnGuests.setBackground(Color.decode("#86BBD8"));

        btnMemberships.setBackground(Color.decode("#86BBD8"));

        btnPayments.setBackground(Color.decode("#86BBD8"));

        btnRooms.setBackground(Color.decode("#86BBD8"));

        refreshHotelTableModel(true);
        
        
        hotelPanel.setVisible(true);
        roomPanel.setVisible(false);
        customerPanel.setVisible(false);
        paymentPanel.setVisible(false);
        bookingPanel.setVisible(false);
        membershipPanel.setVisible(false);
        guestPanel.setVisible(false);
        this.repaint();
        
    }//GEN-LAST:event_btnHotelsActionPerformed

    private void btnGuestsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuestsActionPerformed
        // TODO add your handling code here:
            
            btnGuests.setBackground(Color.decode("#f18973"));
            //btnGuests.setForeground(Color.decode("#86BBD8"));
            
            btnBookings.setBackground(Color.decode("#86BBD8"));
            
            btnCustomers.setBackground(Color.decode("#86BBD8"));
            
            btnHotels.setBackground(Color.decode("#86BBD8"));
            
            btnMemberships.setBackground(Color.decode("#86BBD8"));
            
            btnPayments.setBackground(Color.decode("#86BBD8"));
            
            btnRooms.setBackground(Color.decode("#86BBD8"));
            
            refreshGuestTableModel(true);
            
            hotelPanel.setVisible(false);
            roomPanel.setVisible(false);
            customerPanel.setVisible(false);
            paymentPanel.setVisible(false);
            bookingPanel.setVisible(false);
            membershipPanel.setVisible(false);
            guestPanel.setVisible(true);
            this.repaint();
        
    }//GEN-LAST:event_btnGuestsActionPerformed

    private void btnRoomsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRoomsActionPerformed
        // TODO add your handling code here:
            
            btnRooms.setBackground(Color.decode("#f18973"));
   
            
            btnBookings.setBackground(Color.decode("#86BBD8"));
            
            btnCustomers.setBackground(Color.decode("#86BBD8"));
            
            btnGuests.setBackground(Color.decode("#86BBD8"));
            
            btnMemberships.setBackground(Color.decode("#86BBD8"));
            
            btnPayments.setBackground(Color.decode("#86BBD8"));
            
            btnHotels.setBackground(Color.decode("#86BBD8"));
            
            refreshRoomTableModel(true);
            
            hotelPanel.setVisible(false);
            roomPanel.setVisible(true);
            customerPanel.setVisible(false);
            paymentPanel.setVisible(false);
            bookingPanel.setVisible(false);
            membershipPanel.setVisible(false);
            guestPanel.setVisible(false);
            
            /*
            try
            {
                ArrayList<Facility> facilities = new ArrayList<>(roomController.getRoomFacilities());
                ArrayList<JCheckBox> facilitiesCheckBoxes = new ArrayList<>();

                for (Facility f : facilities)
                {
                    String a = f.getFacilityNumber() + "-" + f.getFacilityName();

                    facilitiesCheckBoxes.add(new JCheckBox(a));
                }

                
                //roomFacilitiesPanel.repaint();
            }
            catch (Exception e)
            {
                
            }
            */
            this.repaint();
    }//GEN-LAST:event_btnRoomsActionPerformed

    private void hotelTypeCodeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotelTypeCodeComboBoxActionPerformed
        // TODO add your handling code here:
        
        try
        {
            // Search by hotelTypeCode
            String code = hotelTypeCodeComboBox.getSelectedItem().toString().split("-")[0];

            if (!code.equals("ALL"))
            {
                hotels = new ArrayList<>(hotelController.getHotelByType(code));
                refreshHotelTableModel(false);
            }
            else
            {
                refreshHotelTableModel(true);
            }
            
        }
        catch (Exception e)
        {
            
        }
        
    }//GEN-LAST:event_hotelTypeCodeComboBoxActionPerformed

    private void roomFacilityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roomFacilityComboBoxActionPerformed
        // TODO add your handling code here:
        try
        {
            // Search by hotelTypeCode
            String code = roomFacilityComboBox.getSelectedItem().toString().split("-")[0];

            if (!code.equals("ALL"))
            {
                rooms = new ArrayList<>(roomController.getRoomByFacilityNumber(Integer.parseInt(code)));
                refreshRoomTableModel(false);
            }
            else
            {
                refreshRoomTableModel(true);
            }
            
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_roomFacilityComboBoxActionPerformed

    private void membershipTierComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_membershipTierComboBoxActionPerformed
        // TODO add your handling code here:
        try
        {
            // Search by hotelTypeCode
            String code = membershipTierComboBox.getSelectedItem().toString().split("-")[0];

            if (!code.equals("ALL"))
            {
                customers = new ArrayList<>(customerController.findCustomerByMembership(code));
                refreshCustomerTableModel(false);
            }
            else
            {
                refreshCustomerTableModel(true);
            }
            
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_membershipTierComboBoxActionPerformed

    private void btnCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCustomersActionPerformed
        
        btnCustomers.setBackground(Color.decode("#f18973"));
       

        btnBookings.setBackground(Color.decode("#86BBD8"));
        
        btnHotels.setBackground(Color.decode("#86BBD8"));
        
        btnGuests.setBackground(Color.decode("#86BBD8"));
        
        btnMemberships.setBackground(Color.decode("#86BBD8"));
        
        btnPayments.setBackground(Color.decode("#86BBD8"));
        
        btnRooms.setBackground(Color.decode("#86BBD8"));
        
        refreshCustomerTableModel(true);
        
        hotelPanel.setVisible(false);
        roomPanel.setVisible(false);
        customerPanel.setVisible(true);
        paymentPanel.setVisible(false);
        bookingPanel.setVisible(false);
        membershipPanel.setVisible(false);
        guestPanel.setVisible(false);
        
        this.repaint();
    }//GEN-LAST:event_btnCustomersActionPerformed

    private void addHotelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addHotelBtnActionPerformed
        // TODO add your handling code here:
        hotel = new Hotel();
        hotel.setHotelName(textHotelName.getText());
        hotel.setHotelTypeCode(hotelTypeCodeComboBox2.getSelectedItem().toString().split("-")[0]);
        
        try{
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy");
            Date date1 = fmt.parse(textHotelConstructionYear.getText());
            // TODO: validate, should be just YYYY
            hotel.setConstructionYear(date1);
            hotel.setContactNumber(textHotelContactNumber.getText());
            hotel.setCountry(textHotelCountry.getText());
            hotel.setCity(textHotelCity.getText());
            hotel.setAddress(textHotelAddress.getText());
            hotel.setEmailAddress(textHotelEmailAddress.getText());
            
            // Add hotel
            hotelController.createHotel(hotel);

            refreshHotelTableModel(true);
            
        }
        catch(Exception e)
        {
            //TODO: Unable to parse date
            
        }
        

        
                
    }//GEN-LAST:event_addHotelBtnActionPerformed

    private void btnMembershipsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMembershipsActionPerformed
        // TODO add your handling code here:
        
        btnMemberships.setBackground(Color.decode("#f18973"));
        
        btnBookings.setBackground(Color.decode("#86BBD8"));
        
        btnCustomers.setBackground(Color.decode("#86BBD8"));
        
        btnGuests.setBackground(Color.decode("#86BBD8"));
        
        btnHotels.setBackground(Color.decode("#86BBD8"));
        
        btnPayments.setBackground(Color.decode("#86BBD8"));
        
        btnRooms.setBackground(Color.decode("#86BBD8"));
        

        refreshMembershipTableModel(true);
        
        hotelPanel.setVisible(false);
        roomPanel.setVisible(false);
        customerPanel.setVisible(false);
        paymentPanel.setVisible(false);
        bookingPanel.setVisible(false);
        membershipPanel.setVisible(true);
        guestPanel.setVisible(false);
        

        
        this.repaint();
    }//GEN-LAST:event_btnMembershipsActionPerformed

    private void btnBookingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBookingsActionPerformed
        // TODO add your handling code here:
        
        btnBookings.setBackground(Color.decode("#f18973"));

        btnHotels.setBackground(Color.decode("#86BBD8"));
        
        btnCustomers.setBackground(Color.decode("#86BBD8"));
        
        btnGuests.setBackground(Color.decode("#86BBD8"));
        
        btnMemberships.setBackground(Color.decode("#86BBD8"));
        
        btnPayments.setBackground(Color.decode("#86BBD8"));
        
        btnRooms.setBackground(Color.decode("#86BBD8"));
        
        hotelPanel.setVisible(false);
        roomPanel.setVisible(false);
        customerPanel.setVisible(false);
        paymentPanel.setVisible(false);
        bookingPanel.setVisible(true);
        membershipPanel.setVisible(false);
        guestPanel.setVisible(false);
        
        refreshBookingTableModel(true);
        
        this.repaint();
    }//GEN-LAST:event_btnBookingsActionPerformed

    private void editPaymentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPaymentBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            int id = Integer.parseInt(textPaymentBookingId.getText());
            int num = Integer.parseInt(textPaymentNumber.getText());
            
            
            payment = FinderService.findPaymentById(payments, id, num);
            
            if (payment != null)
            {
                payment.setPaymentDate(new Date()); // this must always be the latest date you edited the payment
                payment.setPaymentAmount(Double.parseDouble(textPaymentAmount.getText()));
                payment.setCurrencyCode("AUD");
                
                String code = paymentMethodComboBox.getSelectedItem().toString().split("-")[0];
                
                payment.setPaymentMethodCode(code); //TODO: make this dropdown
                
                paymentController.updatePayment(payment);
                
                refreshPaymentTableModel(true);
            }
            else{
                // TODO: error! booking is not found
            }
        }
        catch (Exception e)
        {
            
        }
        
    }//GEN-LAST:event_editPaymentBtnActionPerformed

    private void delBookingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delBookingBtnActionPerformed
        // TODO add your handling code here:
        // can be deleted - also delete the booking room guest
        try
        {
            int id = Integer.parseInt(textBookingId.getText());
            
            ArrayList<Guest> guestList = FinderService.findGuestByBookingId(guests, id);
            
            ArrayList<BookingRoomGuest> brgList = new ArrayList<>(bookingController.getBookingRoomGuest());
            
            ArrayList<BookingRoomGuest> rBrgList = null;
            
            // Delete guests and booking-room-guests
            for (int i = guestList.size() - 1; i >= 0; i--)
            {
                Guest g = guestList.get(i);
                
                int gid = g.getGuestId();
                rBrgList = FinderService.findBookingRoomGuestByGuestId(brgList, gid);
                
                // Also delete the BookingRoomGuest if we're going to delete the room
                // Works like CASCADE
                if (rBrgList != null && rBrgList.size() > 0)
                {
                    for (int h = rBrgList.size() - 1; h >= 0; h--)
                    {
                        BookingRoomGuest brg = rBrgList.get(h);
                        bookingController.deleteBookingRoomGuest(brg);
                    }
                }
                
                guestController.deleteGuest(g);
            }
            
            // Delete payments
            ArrayList<Payment> paymentsList = FinderService.findPaymentByBookingId(payments, id);
            
            if (paymentsList != null && paymentsList.size() > 0)
            {
                for (int i = paymentsList.size() - 1 ; i >= 0; i--)
                {
                    Payment p = paymentsList.get(i);
                    paymentController.deletePayment(p);
                }   
               
            }
            else{
                // TODO: error! booking is not found
            }
                
            
            refreshBookingTableModel(true);
            
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_delBookingBtnActionPerformed

    private void addGuestBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addGuestBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            guest = new Guest();
            
            Booking bkg = new Booking();
            //bkg.setBookingId();
            
            bkg = bookingController.getBookingByBookingId(Integer.parseInt(textGuestBookingId.getText()));
            
            guest.setBooking(bkg);
            guest.setTitle(textGuestTitle.getText());
            guest.setFirstName(textGuestFirstName.getText());
            guest.setLastName(textGuestLastName.getText());

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = fmt.parse(textGuestDob.getText());
            guest.setDob(date1);

            guest.setCountry(textGuestCountry.getText());
            guest.setCity(textGuestCity.getText());
            guest.setStreet(textGuestStreet.getText());
            guest.setPostalCode(textGuestPostalCode.getText());
            guest.setPhoneNumber(textGuestPhoneNumber.getText());
            guest.setEmailAddress(textGuestEmailAddress.getText());
            
            // Add guest
            guestController.createGuest(guest);

            refreshGuestTableModel(true);
            
            // TODO: Sort JTables by ID
            // TODO: Check for duplicates
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
    }//GEN-LAST:event_addGuestBtnActionPerformed

    private void textGuestBookingIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textGuestBookingIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textGuestBookingIdActionPerformed

    private void addCustBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCustBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            customer = new Customer();
            String mc = membershipTierComboBox2.getSelectedItem().toString().split("-")[0];
            membership = membershipController.findMembershipByTierCode(mc);
            
            customer.setMembership(membership);
            customer.setMembershipCredits(Integer.parseInt(textCustomerMembershipCredits.getText()));
             
            customer.setTitle(textCustomerTitle.getText());
            customer.setFirstName(textCustomerFirstName.getText());
            customer.setLastName(textCustomerLastName.getText());

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = fmt.parse(textCustomerDob.getText());
            customer.setDob(date1);

            customer.setCountry(textCustomerCountry.getText());
            customer.setCity(textCustomerCity.getText());
            customer.setStreet(textCustomerStreet.getText());
            customer.setPostalCode(textCustomerPostalCode.getText());
            customer.setPhoneNumber(textCustomerPhoneNumber.getText());
            customer.setEmailAddress(textCustomerEmailAddress.getText());
            
            customerController.createCustomer(customer);

            refreshCustomerTableModel(true);
            
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_addCustBtnActionPerformed

    private void addRoomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRoomBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            room = new Room();
            
            int hotelId = Integer.parseInt(textRoomHotelId.getText());
            RoomType roomType = FinderService.findRoomTypeByCode(roomTypes, textRoomTypeCode.getText());
            
            hotel = FinderService.findHotelById(hotels, hotelId);
            
            if (hotel != null)
            {
                room.setHotelId(hotelId);
            }
            else
            {
                //TODO: throw exception - hotel not found!
            }
            room.setRoomNumber(textRoomNumber.getText());
            room.setRoomDescription(textRoomDescription.getText());
            room.setRoomPrice(Double.parseDouble(textRoomPrice.getText()));
            room.setCurrencyCode("AUD");
            room.setRoomType(roomType);
            
            int newId = roomController.createRoom(room);
            
            refreshRoomTableModel(true);
            
            room = FinderService.findRoomByRoomId(rooms, newId);
            
            //addFacilitiesToRoom(room);
            
        }
        catch (Exception e)
        {
            
        }
        
    }//GEN-LAST:event_addRoomBtnActionPerformed

    /*
    private void addFacilitiesToRoom(Room room)
    {
        try
        {
            airconFacility.setSelected(false);
            wifiFacility.setSelected(false);
            balconyFacility.setSelected(false);
            freeBreakfastFacility.setSelected(false);
            hotAndColdShowerFacility.setSelected(false);
            hairDryerFacility.setSelected(false);
            landlinePhoneFacility.setSelected(false);

            Facility newFacility = null;
            
            // remove existing facilities
            room.getFacilityCollection().clear();

            if (airconFacility.isSelected())
            {
                newFacility = FinderService.findFacilityByName(facilities, "AIRCON");

                room.getFacilityCollection().add(newFacility);
            }

            if (wifiFacility.isSelected())
            {
                newFacility = FinderService.findFacilityByName(facilities, "WIFI");

                room.getFacilityCollection().add(newFacility);
            }

            if (balconyFacility.isSelected())
            {
                newFacility = FinderService.findFacilityByName(facilities, "BALCONY");

                room.getFacilityCollection().add(newFacility);
            }

            if (freeBreakfastFacility.isSelected())
            {
                newFacility = FinderService.findFacilityByName(facilities, "FREE BREAKFAST");

                room.getFacilityCollection().add(newFacility);
            }

            if (hotAndColdShowerFacility.isSelected())
            {
                newFacility = FinderService.findFacilityByName(facilities, "HOT AND COLD SHOWER");

                room.getFacilityCollection().add(newFacility);
            }


            if (hairDryerFacility.isSelected())
            {
                newFacility = FinderService.findFacilityByName(facilities, "HAIR DRYER");

                room.getFacilityCollection().add(newFacility);
            }

            if (landlinePhoneFacility.isSelected())
            {
                newFacility = FinderService.findFacilityByName(facilities, "LANDLINE PHONE");

                room.getFacilityCollection().add(newFacility);
            }

            roomController.updateRoom(room);
        }
        catch (Exception e)
        {
            
        }
            
    }
    */
    
    private void addMembershipBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMembershipBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            membership = new Membership();
            membership.setMembershipTier(textMembershipTierName.getText());
            membership.setMembershipTierCode(textMembershipTierCode.getText());
            membership.setDiscount(Long.parseLong(textMembershipDiscount.getText()));
            membership.setOtherRewards(textMembershipOtherRewards.getText());
            membership.setTierCredits(Integer.parseInt(textMembershipCredit.getText()));
            
            membershipController.createMembership(membership);
            
            refreshMembershipTableModel(true);
            fillUpComboBoxes();
            
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_addMembershipBtnActionPerformed

    private void addPaymentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addPaymentBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            payment = new Payment();
            int id = Integer.parseInt(textPaymentBookingId.getText());
            Booking bkg = bookingController.getBookingByBookingId(id);
            
            if (bkg != null)
            {
                Collection<Payment> bkgPayments = bkg.getPaymentCollection();
                int pnum = 1;

                if (bkgPayments != null)
                {
                    pnum = bkgPayments.size() + 1;
                }

                PaymentPK pk = new PaymentPK();
                pk.setBookingId(id);
                pk.setPaymentNumber(pnum);
                payment.setPaymentPK(pk);
                payment.setBooking(bkg);
                payment.setPaymentDate(new Date()); // TODO: verify this
                payment.setPaymentAmount(Double.parseDouble(textPaymentAmount.getText()));
                payment.setCurrencyCode("AUD");
                String mc = paymentMethodComboBox.getSelectedItem().toString().split("-")[0];
                payment.setPaymentMethodCode(mc); //TODO: make this dropdown
                
                paymentController.createPayment(payment);
                
                refreshPaymentTableModel(true);
            }
            else{
                // TODO: error! booking is not found
            }
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_addPaymentBtnActionPerformed

    private void addBookingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBookingBtnActionPerformed
        // TODO add your handling code here:
        try
        {
        
            booking = new Booking();
            booking.setBookingId(0);
            
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = fmt.parse(textBookingCheckInDate.getText());
            Date date2 = fmt.parse(textBookingCheckOutDate.getText());
            
            booking.setCheckInDate(date1);
            booking.setCheckOutDate(date2);
            booking.setContactEmail(textBookingContactEmail.getText());
            booking.setContactPerson(textBookingContactPerson.getText());
            booking.setCurrencyCode("AUD");

            int id = Integer.parseInt(textBookingCustomerId.getText());

            customer = FinderService.findCustomerByCustomerId(customers, id);

            booking.setCustomer(customer);
            booking.setPaymentStatusCode("UP");
            booking.setTotalAmount(Double.parseDouble(textBookingTotalAmount.getText()));

            bookingController.createBooking(booking);
            
            refreshBookingTableModel(true);
        }
        catch (Exception e)
        {
            
        }

    }//GEN-LAST:event_addBookingBtnActionPerformed

    private void tblHotelValueChanged(ListSelectionEvent evt)
    {
        // TODO add your handling code here:
        Vector data = (Vector)hotelTableModel.getDataVector().elementAt(tblHotel.getSelectedRow());
        
        // data[0] - hotel id
        textHotelId.setText(data.get(0).toString());
        // data[1] - hotel name
        textHotelName.setText(data.get(1).toString());
        //textHotelType.setText(data.get(2).toString());
        
        int index = 0;
        for (String a : htc2String)
        {
            if (a.split("-")[0].equals(data.get(2).toString()))
            {
                index = htc2String.indexOf(a);
                break;
            }
        }
        
        hotelTypeCodeComboBox2.setSelectedIndex(index);
        textHotelConstructionYear.setText(data.get(3).toString());
        textHotelCountry.setText(data.get(4).toString());
        textHotelCity.setText(data.get(5).toString());
        textHotelAddress.setText(data.get(6).toString());
        textHotelContactNumber.setText(data.get(7).toString());
        textHotelEmailAddress.setText(data.get(8).toString());
    }
    
    private void tblBookingValueChanged(ListSelectionEvent evt)
    {
        Vector data = (Vector)bookingTableModel.getDataVector().elementAt(tblBooking.getSelectedRow());
        
        textBookingId.setText(data.get(0).toString());
        textBookingCustomerId.setText(data.get(1).toString());
        textBookingCheckInDate.setText(data.get(2).toString());
        textBookingCheckOutDate.setText(data.get(3).toString());
        textBookingContactPerson.setText(data.get(4).toString());
        textBookingContactEmail.setText(data.get(5).toString());
        textBookingTotalAmount.setText(data.get(6).toString());
        // data.get(7).toString() is "AUD"
        textBookingPaymentStatusCode.setText(data.get(8).toString());
    }
    
    private void tblGuestValueChanged(ListSelectionEvent evt)
    {
        Vector data = (Vector)guestTableModel.getDataVector().elementAt(tblGuest.getSelectedRow());
        textGuestId.setText(data.get(0).toString());
        textGuestBookingId.setText(data.get(1).toString());
        textGuestTitle.setText(data.get(2).toString());
        textGuestFirstName.setText(data.get(3).toString());
        textGuestLastName.setText(data.get(4).toString());
        textGuestDob.setText(data.get(5).toString());
        textGuestCountry.setText(data.get(6).toString());
        textGuestCity.setText(data.get(7).toString());
        textGuestStreet.setText(data.get(8).toString());
        textGuestPostalCode.setText(data.get(9).toString());
        textGuestPhoneNumber.setText(data.get(10).toString());
        textGuestEmailAddress.setText(data.get(11).toString());
        
    }

    private void tblCustomerValueChanged(ListSelectionEvent evt)
    {
        Vector data = (Vector)customerTableModel.getDataVector().elementAt(tblCustomer.getSelectedRow());
        textCustomerId.setText(data.get(0).toString());
        
        int index = 0;
        String dataCode = data.get(1).toString();
        
        for (String a : membership2String)
        {
            if (a.split("-")[0].equals(dataCode))
            {
                index = membership2String.indexOf(a);
                break;
            }
        }
        
        //textCustomerMembershipTier.setText(data.get(1).toString());
        membershipTierComboBox2.setSelectedIndex(index);
        textCustomerMembershipCredits.setText(data.get(2).toString());
        textCustomerTitle.setText(data.get(3).toString());
        textCustomerFirstName.setText(data.get(4).toString());
        textCustomerLastName.setText(data.get(5).toString());
        textCustomerDob.setText(data.get(6).toString());
        textCustomerCountry.setText(data.get(7).toString());
        textCustomerCity.setText(data.get(8).toString());
        textCustomerStreet.setText(data.get(9).toString());
        textCustomerPostalCode.setText(data.get(10).toString());
        textCustomerPhoneNumber.setText(data.get(11).toString());
        textCustomerEmailAddress.setText(data.get(12).toString());
        
    }
    
    private void tblRoomValueChanged(ListSelectionEvent evt)
    {
        Vector data = (Vector)roomTableModel.getDataVector().elementAt(tblRoom.getSelectedRow());
        
        int id = 0;
        try
        {
            id = Integer.parseInt(data.get(0).toString());
        }
        catch (Exception e)
        {
            
        }
        
        /*
        Room room = FinderService.findRoomByRoomId(rooms, id);
        airconFacility.setSelected(false);
        wifiFacility.setSelected(false);
        balconyFacility.setSelected(false);
        freeBreakfastFacility.setSelected(false);
        hotAndColdShowerFacility.setSelected(false);
        hairDryerFacility.setSelected(false);
        landlinePhoneFacility.setSelected(false);
       
        
        Collection<Facility> fcollection = room.getFacilityCollection();
        
        if (fcollection != null && fcollection.size() > 0)
        {
        
            ArrayList<Facility> fList = new ArrayList<>(fcollection);
            
            for (Facility f : fList)
            {
                switch(f.getFacilityName().toUpperCase())
                {
                    case "AIRCON":
                        airconFacility.setSelected(true);
                        break;
                    case "WIFI":
                        wifiFacility.setSelected(true);
                        break;
                    case "BALCONY":
                        balconyFacility.setSelected(true);
                        break;
                    case "FREE BREAKFAST":
                        freeBreakfastFacility.setSelected(true);
                        break;
                    case "HOT AND COLD SHOWER":
                        hotAndColdShowerFacility.setSelected(true);
                        break;
                    case "HAIR DRYER":
                        hairDryerFacility.setSelected(true);
                        break;
                    case "LANDLINE PHONE":
                        landlinePhoneFacility.setSelected(true);
                        break;

                }
            }
        }
        */
        
        textRoomHotelId.setText(data.get(0).toString());
        textRoomId.setText(data.get(1).toString());
        textRoomNumber.setText(data.get(2).toString());
        textRoomTypeCode.setText(data.get(3).toString());
        textRoomPrice.setText(data.get(4).toString());
        textRoomDescription.setText(data.get(6).toString());
        
        
    }
    
    private void tblMembershipValueChanged(ListSelectionEvent evt)
    {
        Vector data = (Vector)membershipTableModel.getDataVector().elementAt(tblMembership.getSelectedRow());
        
        textMembershipTierCode.setText(data.get(0).toString());
        textMembershipTierName.setText(data.get(1).toString());
        textMembershipCredit.setText(data.get(2).toString());
        textMembershipDiscount.setText(data.get(3).toString());
        textMembershipOtherRewards.setText(data.get(4).toString());
    }
    
    private void tblPaymentValueChanged(ListSelectionEvent evt)
    {
         Vector data = (Vector)paymentTableModel.getDataVector().elementAt(tblPayment.getSelectedRow());

         textPaymentBookingId.setText(data.get(0).toString());
         textPaymentNumber.setText(data.get(1).toString());
         textPaymentDate.setText(data.get(2).toString());
         textPaymentAmount.setText(data.get(3).toString());
         
         int index = 0;
         for (String a : pmcString)
         {
             if (a.split("-")[0].equals(data.get(4).toString()))
             {
                 index = pmcString.indexOf(a);
                 break;
             }
         }
         paymentMethodComboBox.setSelectedIndex(index);
         //textPaymentMethodCode.setText(data.get(4).toString());
    }
    
    
    private void editHotelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editHotelBtnActionPerformed
        
        try
        {
            // TODO add your handling code here:
           int id = Integer.parseInt(textHotelId.getText());

           hotel = FinderService.findHotelById(hotels, id);
           
           if (hotel == null)
           {
               // TODO: dialogbox - no hotel found
           }
           
           hotel.setHotelName(textHotelName.getText());
       
           try{
               SimpleDateFormat fmt = new SimpleDateFormat("yyyy");
               Date date1 = fmt.parse(textHotelConstructionYear.getText());
               hotel.setConstructionYear(date1);
               hotel.setContactNumber(textHotelContactNumber.getText());
               hotel.setCountry(textHotelCountry.getText());
               hotel.setCity(textHotelCity.getText());
               hotel.setAddress(textHotelAddress.getText());
               hotel.setEmailAddress(textHotelEmailAddress.getText());
               hotel.setHotelTypeCode(hotelTypeCodeComboBox2.getSelectedItem().toString().split("-")[0]); // TODO: should be drop down

               // Add hotel
               hotelController.updateHotel(hotel);

               refreshHotelTableModel(true);

           }
           catch(Exception e)
           {
               //TODO: Unable to parse date

           }
        }
        catch (Exception e)
        {
            
        }
        
        
    }//GEN-LAST:event_editHotelBtnActionPerformed

    private void editGuestBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editGuestBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            int id = Integer.parseInt(textGuestId.getText());
            guest = FinderService.findGuestByGuestId(guests, id);
            
            Booking bkg = new Booking();
            //bkg.setBookingId();
            
            bkg = bookingController.getBookingByBookingId(Integer.parseInt(textGuestBookingId.getText()));
            
            guest.setBooking(bkg);
            guest.setTitle(textGuestTitle.getText());
            guest.setFirstName(textGuestFirstName.getText());
            guest.setLastName(textGuestLastName.getText());

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = fmt.parse(textGuestDob.getText());
            guest.setDob(date1);

            guest.setCountry(textGuestCountry.getText());
            guest.setCity(textGuestCity.getText());
            guest.setStreet(textGuestStreet.getText());
            guest.setPostalCode(textGuestPostalCode.getText());
            guest.setPhoneNumber(textGuestPhoneNumber.getText());
            guest.setEmailAddress(textGuestEmailAddress.getText());
            
            // Add guest
            guestController.updateGuest(guest);

            refreshGuestTableModel(true);
            
            // TODO: Sort JTables by ID
            // TODO: Check for duplicates
        }
        catch (Exception e)
        {
            // TODO: handle exception
        }
    }//GEN-LAST:event_editGuestBtnActionPerformed

    private void editRoomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editRoomBtnActionPerformed
        // TODO add your handling code here:
         try
        {
            int id = Integer.parseInt(textRoomId.getText());
            room = FinderService.findRoomByRoomId(rooms, id);
            
            int hotelId = Integer.parseInt(textRoomHotelId.getText());
            RoomType roomType = FinderService.findRoomTypeByCode(roomTypes, textRoomTypeCode.getText());
            
            hotel = FinderService.findHotelById(hotels, hotelId);
            
            if (hotel != null)
            {
                room.setHotelId(hotelId);
            }
            else
            {
                //TODO: throw exception - hotel not found!
            }
            room.setRoomNumber(textRoomNumber.getText());
            room.setRoomDescription(textRoomDescription.getText());
            room.setRoomPrice(Double.parseDouble(textRoomPrice.getText()));
            room.setCurrencyCode("AUD");
            room.setRoomType(roomType);
            
            roomController.updateRoom(room);
            
            refreshRoomTableModel(true);
            
        }
        catch (Exception e)
        {
            
        }

    }//GEN-LAST:event_editRoomBtnActionPerformed

    private void editCustomerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editCustomerBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            int id = Integer.parseInt(textCustomerId.getText());
            customer = FinderService.findCustomerByCustomerId(customers, id);
            
            String code = membershipTierComboBox2.getSelectedItem().toString().split("-")[0];
            
            membership = membershipController.findMembershipByTierCode(code);
            
            customer.setMembership(membership);
            customer.setMembershipCredits(Integer.parseInt(textCustomerMembershipCredits.getText()));
             
            customer.setTitle(textCustomerTitle.getText());
            customer.setFirstName(textCustomerFirstName.getText());
            customer.setLastName(textCustomerLastName.getText());

            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = fmt.parse(textCustomerDob.getText());
            customer.setDob(date1);

            customer.setCountry(textCustomerCountry.getText());
            customer.setCity(textCustomerCity.getText());
            customer.setStreet(textCustomerStreet.getText());
            customer.setPostalCode(textCustomerPostalCode.getText());
            customer.setPhoneNumber(textCustomerPhoneNumber.getText());
            customer.setEmailAddress(textCustomerEmailAddress.getText());
            
            customerController.updateCustomer(customer);

            refreshCustomerTableModel(true);
            
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_editCustomerBtnActionPerformed

    private void editMembershipBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editMembershipBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            String code = textMembershipTierCode.getText();
            membership = FinderService.findMembershipByMembershipTierCode(memberships, code);
            membership.setMembershipTier(textMembershipTierName.getText());
            membership.setMembershipTierCode(textMembershipTierCode.getText());
            membership.setDiscount(Long.parseLong(textMembershipDiscount.getText()));
            membership.setOtherRewards(textMembershipOtherRewards.getText());
            membership.setTierCredits(Integer.parseInt(textMembershipCredit.getText()));
            
            membershipController.updateMembership(membership);
            
            refreshMembershipTableModel(true);
            
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_editMembershipBtnActionPerformed

    private void editBookingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBookingBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            int id = Integer.parseInt(textBookingId.getText());
            booking = FinderService.findBookingById(bookings, id);
            
            SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
            Date date1 = fmt.parse(textBookingCheckInDate.getText());
            Date date2 = fmt.parse(textBookingCheckOutDate.getText());
            
            booking.setCheckInDate(date1);
            booking.setCheckOutDate(date2);
            booking.setContactEmail(textBookingContactEmail.getText());
            booking.setContactPerson(textBookingContactPerson.getText());
            booking.setCurrencyCode("AUD");

            int cusid = Integer.parseInt(textBookingCustomerId.getText());

            customer = FinderService.findCustomerByCustomerId(customers, cusid);

            booking.setCustomer(customer);
            booking.setPaymentStatusCode(textBookingPaymentStatusCode.getText());
            booking.setTotalAmount(Double.parseDouble(textBookingTotalAmount.getText()));

            bookingController.updateBooking(booking);
            
            refreshBookingTableModel(true);
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_editBookingBtnActionPerformed

    private void deleteHotelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteHotelBtnActionPerformed
        // TODO add your handling code here:

        try 
        {

            int id = Integer.parseInt(textHotelId.getText());

           hotel = FinderService.findHotelById(hotels, id);
           ArrayList<Room> hotelRooms = FinderService.findRoomByHotelId(rooms, id);
           
           if (hotelRooms.size() <= 0)
           {
               hotelController.deleteHotel(hotel);
           }
           else
           {
               // TODO: Error, cannot delete hotel!
           }
           
           tblHotel.addNotify();
           refreshHotelTableModel(true);
          
        }
        catch (Exception e)
        {
            
        }
        
    }//GEN-LAST:event_deleteHotelBtnActionPerformed

    private void delRoomBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delRoomBtnActionPerformed
        // TODO add your handling code here:
        
        // also delete the bookingroomguest
        // can be deleted
        try
        {
            int id = Integer.parseInt(textRoomId.getText());
            room = FinderService.findRoomByRoomId(rooms, id);
            
            ArrayList<BookingRoomGuest> brgList = new ArrayList<>(bookingController.getBookingRoomGuest());
            
            ArrayList<BookingRoomGuest> rBrgList = FinderService.findBookingRoomGuestByRoomId(brgList, id);
            
            // Also delete the BookingRoomGuest if we're going to delete the room
            // Works like CASCADE
            if (rBrgList.size() > 0)
            {
                for (int i = rBrgList.size() - 1; i >= 0; i--)
                {
                    BookingRoomGuest brg = rBrgList.get(i);
                    bookingController.deleteBookingRoomGuest(brg);
                }
            }
            
            roomController.deleteRoom(id);
            tblRoom.addNotify();
            refreshRoomTableModel(true);
            
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_delRoomBtnActionPerformed

    private void delCustomerBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delCustomerBtnActionPerformed
        // TODO add your handling code here:
        
        // Check if the customer has any booking
        
        // if none, delete!
        
        try
        {
            int id = Integer.parseInt(textCustomerId.getText());
            customer = FinderService.findCustomerByCustomerId(customers, id);
            
            if (customer.getBookingCollection().size() > 0)
            {
                // TODO: Error! Cannot delete customer with booking
            }
            else
            {
                customerController.deleteCustomer(customer);
            }
            
            tblCustomer.addNotify();
            refreshCustomerTableModel(true);

        }
        catch (Exception e)
        {
            
        }
        
        
    }//GEN-LAST:event_delCustomerBtnActionPerformed

    private void delGuestBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delGuestBtnActionPerformed
        // TODO add your handling code here:
        
        // can be deleted - also delete the booking room guest
        try
        {
            int id = Integer.parseInt(textGuestId.getText());
            guest = FinderService.findGuestByGuestId(guests, id);
            
            ArrayList<BookingRoomGuest> brgList = new ArrayList<>(bookingController.getBookingRoomGuest());
            
            ArrayList<BookingRoomGuest> rBrgList = FinderService.findBookingRoomGuestByGuestId(brgList, id);
            
            // Also delete the BookingRoomGuest if we're going to delete the room
            // Works like CASCADE
            if (rBrgList.size() > 0)
            {
                for (int i = rBrgList.size() - 1; i >= 0; i--)
                {
                    BookingRoomGuest brg = rBrgList.get(i);
                    bookingController.deleteBookingRoomGuest(brg);
                }
            }
            
            guestController.deleteGuest(guest);
            tblGuest.addNotify();
            refreshGuestTableModel(true);
            
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_delGuestBtnActionPerformed

    private void delMembershipBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delMembershipBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            String code = textMembershipTierCode.getText();
            membership = FinderService.findMembershipByMembershipTierCode(memberships, code);
            
            if (membership.getCustomerCollection().size() > 0)
            {
                // TODO: error - cannot delete membership
            }
            else
            {
                membershipController.deleteMembership(membership);
                tblMembership.addNotify();
                refreshMembershipTableModel(true);
            }

        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_delMembershipBtnActionPerformed

    private void delPaymentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delPaymentBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            int id = Integer.parseInt(textPaymentBookingId.getText());
            int num = Integer.parseInt(textPaymentNumber.getText());
            
            payment = FinderService.findPaymentById(payments, id, num);
            
            if (payment != null)
            {
                
                paymentController.deletePayment(payment);
                
                tblPayment.addNotify();
                refreshPaymentTableModel(true);
            }
            else{
                // TODO: error! booking is not found
            }
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_delPaymentBtnActionPerformed

    private void hotelTypeCodeComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hotelTypeCodeComboBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hotelTypeCodeComboBox2ActionPerformed

    private void searchGuestBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchGuestBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            String fname = searchGuestFirstName.getText();
            String lname = searchGuestLastName.getText();
            
            guests = new ArrayList<>(guestController.getGuests());
            
            if (!fname.isEmpty() && !lname.isEmpty())
            {
                guests = FinderService.listGuestByGuestName(guests, fname, lname);
            }
            else if (fname.isEmpty() && !lname.isEmpty())
            {
                guests = FinderService.findGuestByLastName(guests, lname);
            }
            else if (!fname.isEmpty() && lname.isEmpty())
            {
                guests = FinderService.findGuestByFirstName(guests, fname);
            }
            
            refreshGuestTableModel(false);
        }
        catch(Exception e)
        {
            
        }
    }//GEN-LAST:event_searchGuestBtnActionPerformed

    private void searchBookingsBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchBookingsBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            String fname = searchBookingFirstName.getText();
            String lname = searchBookingLastName.getText();
            
            bookings = new ArrayList<>(bookingController.getBookings());
            
            if (!fname.isEmpty() && !lname.isEmpty())
            {
                bookings = FinderService.listBookingByCustomerName(bookings, fname, lname);
            }
            else if (fname.isEmpty() && !lname.isEmpty())
            {
                bookings = FinderService.findBookingByLastName(bookings, lname);
            }
            else if (!fname.isEmpty() && lname.isEmpty())
            {
                bookings = FinderService.findBookingByFirstName(bookings, fname);
            }
            
            refreshBookingTableModel(false);
        }
        catch(Exception e)
        {
            
        }
    }//GEN-LAST:event_searchBookingsBtnActionPerformed

    private void searchPaymentBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchPaymentBtnActionPerformed
        // TODO add your handling code here:
        try
        {
            String fname = searchPaymentFirstName.getText();
            String lname = searchPaymentLastName.getText();
            
            payments = new ArrayList<>(paymentController.getPayments());
            
            if (!fname.isEmpty() && !lname.isEmpty())
            {
                payments = FinderService.listPaymentByCustomerName(payments, fname, lname);
            }
            else if (fname.isEmpty() && !lname.isEmpty())
            {
                payments = FinderService.findPaymentByLastName(payments, lname);
            }
            else if (!fname.isEmpty() && lname.isEmpty())
            {
                payments = FinderService.findPaymentByFirstName(payments, fname);
            }
            
            refreshPaymentTableModel(false);
        }
        catch(Exception e)
        {
            
        }
    }//GEN-LAST:event_searchPaymentBtnActionPerformed

    private void searchMemberBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMemberBtnActionPerformed
        // TODO add your handling code here:
        
        try
        {
            int availableCredits = Integer.parseInt(searchMembershipAvailableCredit.getText());
            
            memberships = new ArrayList<>(membershipController.findMembershipByTierCredits(availableCredits));
            
            refreshMembershipTableModel(false);
        }
        catch (Exception e)
        {
            
        }
    }//GEN-LAST:event_searchMemberBtnActionPerformed

    private void createBookingBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createBookingBtnActionPerformed
        // TODO add your handling code here:
        BookingCreator bookingCreator = new BookingCreator(emfactoryList);
        bookingCreator.setVisible(true);
    }//GEN-LAST:event_createBookingBtnActionPerformed

    
    private void refreshHotelTableModel(boolean getAll)
    {
        try
        {
            Object dataHotel [][] = {{}};
            hotelTableModel = new DefaultTableModel(dataHotel, hotelColumnHeaders);
            
            if (getAll)
            {
                hotels = new ArrayList<>(hotelController.getHotels());
            }

            for (Hotel h : hotels)
            {
                Object [] rowData = new Object[9];
                rowData[0] = h.getHotelId();
                rowData[1] = h.getHotelName();
                rowData[2] = h.getHotelTypeCode();
                Calendar cal = Calendar.getInstance();
                cal.setTime(h.getConstructionYear());
                rowData[3] = cal.get(Calendar.YEAR);
                rowData[4] = h.getCountry();
                rowData[5] = h.getCity();
                rowData[6] = h.getAddress();
                rowData[7] = h.getContactNumber();
                rowData[8] = h.getEmailAddress();

                hotelTableModel.addRow(rowData);
            }

            tblHotel.addNotify();
            tblHotel.setModel(hotelTableModel);
            
            tblHotel.clearSelection();
            tblHotel.repaint();
            
        }
        catch (Exception e)
        {
            
        }
    }
    
    private void refreshBookingTableModel(boolean getAll)
    {
        try
        {
            Object dataBooking [][] = {{}};
            bookingTableModel = new DefaultTableModel(dataBooking, bookingColumnHeaders);
            
            if (getAll)
            {
                bookings = new ArrayList<>(bookingController.getBookings());
            }

            for (Booking h : bookings)
            {
                Object [] rowData = new Object[9];
                rowData[0] = h.getBookingId();
                rowData[1] = h.getCustomer().getCustomerId();
                rowData[2] = getDateString(h.getCheckInDate());
                rowData[3] = getDateString(h.getCheckOutDate());
                rowData[4] = h.getContactPerson();
                rowData[5] = h.getContactEmail();
                rowData[6] = h.getTotalAmount();
                rowData[7] = h.getCurrencyCode();
                rowData[8] = h.getPaymentStatusCode();

                bookingTableModel.addRow(rowData);
            }

            tblBooking.addNotify();
            tblBooking.setModel(bookingTableModel);

            tblBooking.clearSelection();
            tblBooking.repaint();
        }
        catch (Exception e)
        {
            
        }
    }
    
    private void refreshRoomTableModel(boolean getAll)
    {
        try
        {
            Object dataRoom [][] = {{}};
            roomTableModel = new DefaultTableModel(dataRoom, roomColumnHeaders);
            
            if (getAll)
            {
                rooms = new ArrayList<>(roomController.getRooms());
            }

            for (Room h : rooms)
            {
                Object [] rowData = new Object[7];
                rowData[0] = h.getHotelId();
                rowData[1] = h.getRoomId();
                rowData[2] = h.getRoomNumber();
                rowData[3] = h.getRoomType().getRoomTypeCode();
                rowData[4] = h.getRoomPrice();
                rowData[5] = h.getCurrencyCode();
                rowData[6] = h.getRoomDescription();

                roomTableModel.addRow(rowData);
            }

            tblRoom.addNotify();
            tblRoom.setModel(roomTableModel);
            tblRoom.clearSelection();
            tblRoom.repaint();
        }
        catch (Exception e)
        {
            
        }
    }
    
    private void refreshGuestTableModel(boolean getAll)
    {
        try 
        {
            Object dataGuest [][] = {{}};
            guestTableModel = new DefaultTableModel(dataGuest, guestColumnHeaders);
            
            if(getAll)
            {
                guests = new ArrayList<>(guestController.getGuests());
            }
            
            for (Guest h : guests)
            {
                Object [] rowData = new Object[12];
                rowData[0] = h.getGuestId();
                rowData[1] = h.getBooking().getBookingId();
                rowData[2] = h.getTitle();
                rowData[3] = h.getFirstName();
                rowData[4] = h.getLastName();
                rowData[5] = getDateString(h.getDob());
                rowData[6] = h.getCountry();
                rowData[7] = h.getCity();
                rowData[8] = h.getStreet();
                rowData[9] = h.getPostalCode();
                rowData[10] = h.getPhoneNumber();
                rowData[11] = h.getEmailAddress();

                guestTableModel.addRow(rowData);
            }

            tblGuest.addNotify();
            tblGuest.setModel(guestTableModel);
            tblGuest.clearSelection();
            tblGuest.repaint();
            
        }
        catch(Exception e)
        {
            
        }
    }
    
    private void refreshPaymentTableModel(boolean getAll)
    {
        try
        {
            Object dataPayment [][] = {{}};
            paymentTableModel = new DefaultTableModel(dataPayment, paymentColumnHeaders);
            
            if (getAll)
            {
                payments = new ArrayList<>(paymentController.getPayments());
            }

            
            for (Payment p : payments)
            {
                Object [] rowData = new Object[6];
                rowData[0] = p.getPaymentPK().getBookingId();
                rowData[1] = p.getPaymentPK().getPaymentNumber();
   
               
                
                rowData[2] = getDateString(p.getPaymentDate());//p.getPaymentDate();
                rowData[3] = p.getPaymentAmount();
                rowData[4] = p.getCurrencyCode();
                rowData[5] = p.getPaymentMethodCode();

                paymentTableModel.addRow(rowData);
            }

            tblPayment.addNotify();
            tblPayment.setModel(paymentTableModel);
            tblPayment.clearSelection();
            tblPayment.repaint();
            
        }
        catch (Exception e)
        {
            
        }
    }
    
    private void refreshCustomerTableModel(boolean getAll)
    {
        try
        {
            Object dataCustomer [][] = {{}};
            customerTableModel = new DefaultTableModel(dataCustomer, customerColumnHeaders);
            
            if (getAll)
            {
                customers = new ArrayList<>(customerController.getCustomers());
            }
            
            for (Customer h : customers)
            {
                Object [] rowData = new Object[13];
                rowData[0] = h.getCustomerId();
                rowData[1] = h.getMembership().getMembershipTierCode();
                rowData[2] = h.getMembershipCredits();
                rowData[3] = h.getTitle();
                rowData[4] = h.getFirstName();
                rowData[5] = h.getLastName();
                rowData[6] = getDateString(h.getDob());
                rowData[7] = h.getCountry();
                rowData[8] = h.getCity();
                rowData[9] = h.getStreet();
                rowData[10] = h.getPostalCode();
                rowData[11] = h.getPhoneNumber();
                rowData[12] = h.getEmailAddress();

                customerTableModel.addRow(rowData);
            }

            tblCustomer.addNotify();
            tblCustomer.setModel(customerTableModel);
            tblCustomer.clearSelection();
            tblCustomer.repaint();

        }
        catch (Exception e)
        {
            
        }
    }
    
    private void refreshMembershipTableModel(boolean getAll)
    {
        try
        {
            Object dataMembership [][] = {{}};

            membershipTableModel = new DefaultTableModel(dataMembership, membershipColumnHeaders);

            if (getAll)
            {
                memberships = new ArrayList<>(membershipController.getMemberships());
            }

            for (Membership m : memberships)
            {
                Object [] rowData = new Object[5];
                rowData[0] = m.getMembershipTierCode();
                rowData[1] = m.getMembershipTier();
                rowData[2] = m.getTierCredits();
                rowData[3] = m.getDiscount();
                rowData[4] = m.getOtherRewards();

                membershipTableModel.addRow(rowData);
            }

            tblMembership.addNotify();
            tblMembership.setModel(membershipTableModel);
            tblMembership.clearSelection();
            tblMembership.repaint();
        }
        catch (Exception e)
        {
            
        }

    }
    
    private void refreshDataTableModels()
    {
        try{
            
            refreshHotelTableModel(true);
            refreshBookingTableModel(true);
            refreshRoomTableModel(true);
            refreshGuestTableModel(true);
            refreshCustomerTableModel(true);
            refreshMembershipTableModel(true);
            refreshPaymentTableModel(true);
           
        }
        catch (Exception e)
        {
            
        }
    }
    
    private String getDateString(Date date)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        DecimalFormat mFormat= new DecimalFormat("00");

        String dataDate = date.toString();

        String mo = cal.getTime().toString();
        mo = cal.get(Calendar.YEAR) + 
                "-" + 
                mFormat.format((cal.get(Calendar.MONTH) + 1)%12).toString() 
                + "-" + mFormat.format(cal.get(Calendar.DATE)).toString();
        
        return mo;
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBookingBtn;
    private javax.swing.JButton addCustBtn;
    private javax.swing.JButton addGuestBtn;
    private javax.swing.JButton addHotelBtn;
    private javax.swing.JButton addMembershipBtn;
    private javax.swing.JButton addPaymentBtn;
    private javax.swing.JButton addRoomBtn;
    private javax.swing.JPanel bookingPanel;
    private javax.swing.JButton btnBookings;
    private javax.swing.JButton btnCustomers;
    private javax.swing.JButton btnGuests;
    private javax.swing.JButton btnHotels;
    private javax.swing.JButton btnMemberships;
    private javax.swing.JButton btnPayments;
    private javax.swing.JButton btnRooms;
    private javax.swing.JButton createBookingBtn;
    private javax.swing.JPanel customerPanel;
    private javax.swing.JButton delBookingBtn;
    private javax.swing.JButton delCustomerBtn;
    private javax.swing.JButton delGuestBtn;
    private javax.swing.JButton delMembershipBtn;
    private javax.swing.JButton delPaymentBtn;
    private javax.swing.JButton delRoomBtn;
    private javax.swing.JButton deleteHotelBtn;
    private javax.swing.JButton editBookingBtn;
    private javax.swing.JButton editCustomerBtn;
    private javax.swing.JButton editGuestBtn;
    private javax.swing.JButton editHotelBtn;
    private javax.swing.JButton editMembershipBtn;
    private javax.swing.JButton editPaymentBtn;
    private javax.swing.JButton editRoomBtn;
    private javax.swing.JPanel guestPanel;
    private javax.swing.JLabel hotelAddress;
    private javax.swing.JLabel hotelCity;
    private javax.swing.JLabel hotelConstructionYear;
    private javax.swing.JLabel hotelContactNumber;
    private javax.swing.JLabel hotelCountry;
    private javax.swing.JLabel hotelEmailAddress;
    private javax.swing.JLabel hotelName;
    private javax.swing.JPanel hotelPanel;
    private javax.swing.JLabel hotelType;
    private javax.swing.JComboBox<String> hotelTypeCodeComboBox;
    private javax.swing.JComboBox<String> hotelTypeCodeComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel100;
    private javax.swing.JLabel jLabel101;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JLabel labelGuestBookingId;
    private javax.swing.JPanel membershipPanel;
    private javax.swing.JComboBox<String> membershipTierComboBox;
    private javax.swing.JComboBox<String> membershipTierComboBox2;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel menuPanel2;
    private javax.swing.JComboBox<String> paymentMethodComboBox;
    private javax.swing.JPanel paymentPanel;
    private javax.swing.JComboBox<String> roomFacilityComboBox;
    private javax.swing.JPanel roomPanel;
    private javax.swing.JTextField searchBookingFirstName;
    private javax.swing.JTextField searchBookingLastName;
    private javax.swing.JButton searchBookingsBtn;
    private javax.swing.JButton searchGuestBtn;
    private javax.swing.JTextField searchGuestFirstName;
    private javax.swing.JTextField searchGuestLastName;
    private javax.swing.JButton searchMemberBtn;
    private javax.swing.JTextField searchMembershipAvailableCredit;
    private javax.swing.JButton searchPaymentBtn;
    private javax.swing.JTextField searchPaymentFirstName;
    private javax.swing.JTextField searchPaymentLastName;
    private javax.swing.JTable tblBooking;
    private javax.swing.JTable tblCustomer;
    private javax.swing.JTable tblGuest;
    private javax.swing.JTable tblHotel;
    private javax.swing.JTable tblMembership;
    private javax.swing.JTable tblPayment;
    private javax.swing.JTable tblRoom;
    private javax.swing.JTextField textBookingCheckInDate;
    private javax.swing.JTextField textBookingCheckOutDate;
    private javax.swing.JTextField textBookingContactEmail;
    private javax.swing.JTextField textBookingContactPerson;
    private javax.swing.JTextField textBookingCustomerId;
    private javax.swing.JTextField textBookingId;
    private javax.swing.JTextField textBookingPaymentStatusCode;
    private javax.swing.JTextField textBookingTotalAmount;
    private javax.swing.JTextField textCustomerCity;
    private javax.swing.JTextField textCustomerCountry;
    private javax.swing.JTextField textCustomerDob;
    private javax.swing.JTextField textCustomerEmailAddress;
    private javax.swing.JTextField textCustomerFirstName;
    private javax.swing.JTextField textCustomerId;
    private javax.swing.JTextField textCustomerLastName;
    private javax.swing.JTextField textCustomerMembershipCredits;
    private javax.swing.JTextField textCustomerPhoneNumber;
    private javax.swing.JTextField textCustomerPostalCode;
    private javax.swing.JTextField textCustomerStreet;
    private javax.swing.JTextField textCustomerTitle;
    private javax.swing.JTextField textGuestBookingId;
    private javax.swing.JTextField textGuestCity;
    private javax.swing.JTextField textGuestCountry;
    private javax.swing.JTextField textGuestDob;
    private javax.swing.JTextField textGuestEmailAddress;
    private javax.swing.JTextField textGuestFirstName;
    private javax.swing.JTextField textGuestId;
    private javax.swing.JTextField textGuestLastName;
    private javax.swing.JTextField textGuestPhoneNumber;
    private javax.swing.JTextField textGuestPostalCode;
    private javax.swing.JTextField textGuestStreet;
    private javax.swing.JTextField textGuestTitle;
    private javax.swing.JTextField textHotelAddress;
    private javax.swing.JTextField textHotelCity;
    private javax.swing.JTextField textHotelConstructionYear;
    private javax.swing.JTextField textHotelContactNumber;
    private javax.swing.JTextField textHotelCountry;
    private javax.swing.JTextField textHotelEmailAddress;
    private javax.swing.JTextField textHotelId;
    private javax.swing.JTextField textHotelName;
    private javax.swing.JTextField textMembershipCredit;
    private javax.swing.JTextField textMembershipDiscount;
    private javax.swing.JTextField textMembershipOtherRewards;
    private javax.swing.JTextField textMembershipTierCode;
    private javax.swing.JTextField textMembershipTierName;
    private javax.swing.JTextField textPaymentAmount;
    private javax.swing.JTextField textPaymentBookingId;
    private javax.swing.JTextField textPaymentDate;
    private javax.swing.JTextField textPaymentNumber;
    private javax.swing.JTextField textRoomDescription;
    private javax.swing.JTextField textRoomHotelId;
    private javax.swing.JTextField textRoomId;
    private javax.swing.JTextField textRoomNumber;
    private javax.swing.JTextField textRoomPrice;
    private javax.swing.JTextField textRoomTypeCode;
    // End of variables declaration//GEN-END:variables
}
