CREATE TABLE users_type
(
  id_users_type INT NOT NULL,
  name_users_type VARCHAR(100) NOT NULL,
  PRIMARY KEY (id_users_type)
);

CREATE TABLE users
(
  id_users INT NOT NULL,
  email_users VARCHAR(150) NOT NULL,
  password_users VARCHAR(150) NOT NULL,
  alamat_users TEXT NOT NULL,
  id_users_type INT NOT NULL,
  PRIMARY KEY (id_users),
  FOREIGN KEY (id_users_type) REFERENCES users_type(id_users_type)
);

CREATE TABLE transaction
(
  id_transaction INT NOT NULL,
  name_transaction VARCHAR(250) NOT NULL,
  weight_transaction VARCHAR(10) NOT NULL,
  desc_transaction TEXT NOT NULL,
  id_users INT NOT NULL,
  PRIMARY KEY (id_transaction),
  FOREIGN KEY (id_users) REFERENCES users(id_users)
);

CREATE TABLE transaction_approval
(
  id_approval INT NOT NULL,
  id_status_transaction INT NOT NULL,
  create_date DATE NOT NULL,
  id_transaction INT NOT NULL,
  PRIMARY KEY (id_approval),
  FOREIGN KEY (id_transaction) REFERENCES transaction(id_transaction)
);
