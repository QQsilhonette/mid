INSERT INTO `t_template` (`id`, `platform`, `tenant_id`, `template_type`, `template_name`, `description`, `active`, `system_env`, `created_by`, `gmt_create`, `last_modified_by`, `gmt_modified`)
VALUES
       (6, 0, '', 'electronic_log_template', '日志模版', '', 1, 'dev', '02309', 1517906411600, '02309', 1517906411600);


INSERT INTO `t_template_field` (`id`, `platform`, `tenant_id`, `field_type`, `field_name`, `display_name`, `description`, `value_type`, `search_mode`, `active`, `extension`, `system_env`, `gmt_create`, `gmt_modified`)
VALUES
       (41, 0, '', 'System', 'firmwareName', '固件名称', '固件名称', 'string', 1, 1, '', 'dev', 1517906411600, 1517906411600)

INSERT INTO `t_template_form` (`id`, `form_key`, `template_id`, `form_name`, `description`, `active`, `system_env`, `is_delete`, `created_by`, `gmt_create`, `last_modified_by`, `gmt_modified`)
VALUES
       (6, 'electronic_log_firmware_base', 6, '固件基础信息', '固件基础信息', 1, 'dev', 0, '02309', 1517906411600, '02309', 1517906411600)
;

INSERT INTO `t_template_form_field_rel` (`id`, `form_id`, `field_id`, `type_name`, `default_value`, `placeholder`, `props`, `extension`, `validations`, `required`, `client_permission`, `backstage_permission`, `sort_order`, `gmt_create`, `gmt_modified`, `is_delete`)
VALUES
       (41, 6, 41, 'Text', '', '', '', '', '', 0, 3, 3, 4, 1517906411600, 1517906411600, 0)
;