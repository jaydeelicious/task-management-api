CREATE TABLE tasks
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    status      VARCHAR(50) NOT NULL,
    priority    VARCHAR(50) NOT NULL,
    due_date    DATE,
    created_at  TIMESTAMPTZ NOT NULL,
    updated_at  TIMESTAMPTZ NOT NULL,
    project_id  BIGINT NOT NULL,

    CONSTRAINT fk_tasks_project
        FOREIGN KEY (project_id)
            REFERENCES projects(id)
);