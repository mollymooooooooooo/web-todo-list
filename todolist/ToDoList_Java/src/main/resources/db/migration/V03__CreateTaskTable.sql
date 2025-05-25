DO $$ BEGIN
    CREATE TYPE TASK_STATUS AS ENUM ('Active', 'Completed', 'Overdue', 'Late');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
    CREATE TYPE TASK_PRIORITY AS ENUM ('Low', 'Medium', 'High', 'Critical');
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

CREATE TABLE IF NOT EXISTS task
(
    id          UUID         NOT NULL,
    user_id     UUID,
    title       VARCHAR(200) NOT NULL,
    description VARCHAR(1024),
    status      TASK_STATUS  NOT NULL,
    priority    TASK_PRIORITY,
    deadline    DATE,
    create_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    update_time TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_task PRIMARY KEY (id)
);


DO $$ BEGIN
    ALTER TABLE task
        ADD CONSTRAINT FK_TASK_ON_USER FOREIGN KEY (user_id) REFERENCES "user" (id);
EXCEPTION
    WHEN duplicate_object THEN null;
END $$;