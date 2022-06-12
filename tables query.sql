

-- --------------------------------------------------------

--
-- Table structure for table `items_table`
--

CREATE TABLE `items_table` (
  `ID` bigint(20) NOT NULL,
  `Name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Price` double NOT NULL,
  `TaxPercentage` int(11) NOT NULL,
  `Quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- --------------------------------------------------------

--
-- Table structure for table `item_log_table`
--

CREATE TABLE `item_log_table` (
  `ID` int(11) NOT NULL,
  `Item_id` bigint(20) NOT NULL,
  `Date` datetime NOT NULL,
  `Type_of_operation` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `oldName` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `oldPrice` double NOT NULL,
  `oldTaxPercentage` int(11) NOT NULL,
  `oldQuantity` int(11) NOT NULL,
  `newName` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `newPrice` double NOT NULL,
  `newTaxPercentage` int(11) NOT NULL,
  `newQuantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `receipt_table`
--

CREATE TABLE `receipt_table` (
  `ID` int(11) NOT NULL,
  `Date` datetime DEFAULT NULL,
  `TotalPrice` double DEFAULT NULL,
  `Paidamount` double DEFAULT NULL,
  `Changeamount` double DEFAULT NULL,
  `Type` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `TotalTax` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sells_table`
--

CREATE TABLE `sells_table` (
  `ID` int(11) NOT NULL,
  `Item_id` bigint(20) NOT NULL,
  `Name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Quantity` int(11) NOT NULL,
  `Price` double NOT NULL,
  `Tax` double NOT NULL,
  `Recipt_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- --------------------------------------------------------

--
-- Table structure for table `staff_log_table`
--

CREATE TABLE `staff_log_table` (
  `ID` int(11) NOT NULL,
  `Date` datetime NOT NULL,
  `Type_of_operation` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `staffID` int(11) NOT NULL,
  `oldIdNum` bigint(20) NOT NULL,
  `oldFirstName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `oldLastName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `oldPhone` bigint(20) NOT NULL,
  `oldEmail` varchar(200) COLLATE utf8_unicode_ci NOT NULL,
  `oldPosition` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `oldSalary` double NOT NULL,
  `newIdNum` bigint(20) NOT NULL,
  `newFirstName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `newLastName` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `newPhone` bigint(20) NOT NULL,
  `newEmail` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `newPosition` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `newSalary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `staff_table`
--

CREATE TABLE `staff_table` (
  `ID` int(11) NOT NULL,
  `IdNum` bigint(20) NOT NULL,
  `FirstName` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `LastName` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Phone` bigint(20) DEFAULT NULL,
  `Email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `Position` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Salary` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;



-- --------------------------------------------------------

--
-- Table structure for table `user_table`
--

CREATE TABLE `user_table` (
  `ID` int(11) NOT NULL,
  `Name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `Password` varchar(45) COLLATE utf8_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Dumping data for table `user_table`
--

INSERT INTO `user_table` (`ID`, `Name`, `Password`) VALUES
(1, 'admin', 'admin');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `items_table`
--
ALTER TABLE `items_table`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `item_log_table`
--
ALTER TABLE `item_log_table`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `receipt_table`
--
ALTER TABLE `receipt_table`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `Type_idx` (`Type`);

--
-- Indexes for table `sells_table`
--
ALTER TABLE `sells_table`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `recipt_id_idx` (`Recipt_id`);

--
-- Indexes for table `staff_log_table`
--
ALTER TABLE `staff_log_table`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `staff_table`
--
ALTER TABLE `staff_table`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `user_table`
--
ALTER TABLE `user_table`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `ID_UNIQUE` (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `item_log_table`
--
ALTER TABLE `item_log_table`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `receipt_table`
--
ALTER TABLE `receipt_table`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sells_table`
--
ALTER TABLE `sells_table`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `staff_log_table`
--
ALTER TABLE `staff_log_table`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `staff_table`
--
ALTER TABLE `staff_table`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_table`
--
ALTER TABLE `user_table`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `sells_table`
--
ALTER TABLE `sells_table`
  ADD CONSTRAINT `recipt_id` FOREIGN KEY (`Recipt_id`) REFERENCES `receipt_table` (`ID`);

