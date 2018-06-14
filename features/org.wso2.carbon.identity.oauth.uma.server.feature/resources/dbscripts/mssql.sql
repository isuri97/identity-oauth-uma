IF NOT  EXISTS (SELECT * FROM SYS.OBJECTS WHERE OBJECT_ID = OBJECT_ID(N'[DBO].[IDN_UMA_RESOURCE]') AND TYPE IN (N'U'))
CREATE TABLE IDN_UMA_RESOURCE (
  ID                  INTEGER IDENTITY NOT NULL,
  RESOURCE_ID         VARCHAR(255),
  RESOURCE_NAME       VARCHAR(255),
  TIME_CREATED        DATETIME         NOT NULL,
  RESOURCE_OWNER_NAME VARCHAR(255),
  CLIENT_ID           VARCHAR(255),
  TENANT_ID           INTEGER DEFAULT -1234,
  USER_DOMAIN         VARCHAR(50),
  PRIMARY KEY (ID)
);

CREATE INDEX IDX_RID ON IDN_UMA_RESOURCE (RESOURCE_ID);

CREATE INDEX IDX_USER ON IDN_UMA_RESOURCE (RESOURCE_OWNER_NAME, USER_DOMAIN);

IF NOT EXISTS ( SELECT * FROM SYS.OBJECTS WHERE OBJECT_ID = OBJECT_ID(N'[DBO].[IDN_UMA_RESOURCE_META_DATA]') AND TYPE IN (N'U'))
CREATE TABLE IDN_UMA_RESOURCE_META_DATA (
  ID                INTEGER IDENTITY NOT NULL,
  RESOURCE_IDENTITY INTEGER                NOT NULL,
  PROPERTY_KEY      VARCHAR(40),
  PROPERTY_VALUE    VARCHAR(255),
  PRIMARY KEY (ID),
  FOREIGN KEY (RESOURCE_IDENTITY) REFERENCES IDN_UMA_RESOURCE (ID) ON DELETE CASCADE
);

IF NOT EXISTS ( SELECT * FROM SYS.OBJECTS WHERE OBJECT_ID = OBJECT_ID(N'[DBO].[IDN_UMA_RESOURCE_META_DATA]') AND TYPE IN (N'U'))
CREATE TABLE IDN_UMA_RESOURCE_SCOPE (
  ID                INTEGER IDENTITY NOT NULL,
  RESOURCE_IDENTITY INTEGER                NOT NULL,
  SCOPE_NAME        VARCHAR(255),
  PRIMARY KEY (ID),
  FOREIGN KEY (RESOURCE_IDENTITY) REFERENCES IDN_UMA_RESOURCE (ID) ON DELETE CASCADE
);

CREATE INDEX IDX_RS ON IDN_UMA_RESOURCE_SCOPE (SCOPE_NAME);

IF NOT EXISTS ( SELECT * FROM SYS.OBJECTS WHERE OBJECT_ID = OBJECT_ID(N'[DBO].[IDN_UMA_PERMISSION_TICKET]') AND TYPE IN (N'U'))
CREATE TABLE IDN_UMA_PERMISSION_TICKET (
  ID              INTEGER IDENTITY NOT NULL,
  PT              VARCHAR(255)     NOT NULL,
  TIME_CREATED    DATETIME         NOT NULL,
  EXPIRY_TIME     DATETIME         NOT NULL,
  TICKET_STATE    VARCHAR(25) DEFAULT 'ACTIVE',
  TENANT_ID       INTEGER     DEFAULT -1234,
  PRIMARY KEY (ID)
);

CREATE INDEX IDX_PT ON IDN_UMA_PERMISSION_TICKET (PT);

IF NOT EXISTS ( SELECT * FROM SYS.OBJECTS WHERE OBJECT_ID = OBJECT_ID(N'[DBO].[IDN_UMA_PT_RESOURCE]') AND TYPE IN (N'U'))
CREATE TABLE IDN_UMA_PT_RESOURCE (
  ID             INTEGER IDENTITY NOT NULL,
  PT_RESOURCE_ID INTEGER                NOT NULL,
  PT_ID          INTEGER                NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (PT_ID) REFERENCES IDN_UMA_PERMISSION_TICKET (ID) ON DELETE CASCADE,
  FOREIGN KEY (PT_RESOURCE_ID) REFERENCES IDN_UMA_RESOURCE (ID) ON DELETE CASCADE
);

IF NOT EXISTS ( SELECT * FROM SYS.OBJECTS WHERE OBJECT_ID = OBJECT_ID(N'[DBO].[IDN_UMA_PT_RESOURCE_SCOPE]') AND TYPE IN (N'U'))
CREATE TABLE IDN_UMA_PT_RESOURCE_SCOPE (
  ID             INTEGER IDENTITY NOT NULL,
  PT_RESOURCE_ID INTEGER                NOT NULL,
  PT_SCOPE_ID    INTEGER                NOT NULL,
  PRIMARY KEY (ID),
  FOREIGN KEY (PT_RESOURCE_ID) REFERENCES IDN_UMA_PT_RESOURCE (ID) ON DELETE CASCADE,
  FOREIGN KEY (PT_SCOPE_ID) REFERENCES IDN_UMA_RESOURCE_SCOPE (ID) ON DELETE CASCADE
);
