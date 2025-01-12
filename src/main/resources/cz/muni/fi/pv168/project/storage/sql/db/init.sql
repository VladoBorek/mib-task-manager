--
-- Category table definition
--
CREATE TABLE IF NOT EXISTS "Category"
(
    `id`    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `name`  VARCHAR(100) NOT NULL UNIQUE,
    `color` BIGINT       NOT NULL
);

--
-- TimeUnit table definition
--
CREATE TABLE IF NOT EXISTS "TimeUnit"
(
    `id`        BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `name`      VARCHAR(100)   NOT NULL UNIQUE,
    `shortName` VARCHAR(10)    NOT NULL,
    `rate`      DECIMAL(10, 2) NOT NULL
);

--
-- Task table definition
--
CREATE TABLE IF NOT EXISTS "Task"
(
    `id`            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `status`        VARCHAR(50)     NOT NULL,
    `description`   TEXT            NOT NULL,
    `categoryId`    BIGINT          NOT NULL REFERENCES "Category" (`id`),
    `customer`      VARCHAR(150),
    `name`          VARCHAR(150)    NOT NULL,
    `assignedTo`    VARCHAR(150),
    `loggedTime`    DECIMAL(255, 4) NOT NULL,
    `allocatedTime` DECIMAL(255, 4) NOT NULL,
    `timeUnitId`    BIGINT REFERENCES "TimeUnit" (`id`),
    `dueDate`       DATE
);

--
-- Template table definition
--
CREATE TABLE IF NOT EXISTS "Template"
(
    `id`            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `description`   TEXT            NOT NULL,
    `categoryId`    BIGINT          NOT NULL REFERENCES "Category" (`id`),
    `templateName`  VARCHAR(255)    NOT NULL UNIQUE,
    `taskName`      VARCHAR(255)    NOT NULL,
    `assignedTo`    VARCHAR(255),
    `allocatedTime` DECIMAL(255, 4) NOT NULL,
    `timeUnitId`    BIGINT REFERENCES "TimeUnit" (`id`)
);

--
-- LogTimeInfo table definition
--
CREATE TABLE IF NOT EXISTS "LogTimeInfo"
(
    `id`         BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    `loggedTime` DECIMAL(255, 4) NOT NULL,
    `userName`   VARCHAR(255)    NOT NULL,
    `userId`     BIGINT          NOT NULL,
    `taskId`     BIGINT          NOT NULL,
    `timeUnitId` BIGINT,
    FOREIGN KEY (`taskId`) REFERENCES "Task" (`id`),
    FOREIGN KEY (`timeUnitId`) REFERENCES "TimeUnit" (`id`)
);
