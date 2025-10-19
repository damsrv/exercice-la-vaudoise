CREATE OR REPLACE FUNCTION get_active_contracts_sum(p_client_id BIGINT)
RETURNS NUMERIC(19,2)
LANGUAGE plpgsql
AS $$
BEGIN
RETURN COALESCE(
        (SELECT SUM(cost_amount)
         FROM contracts
         WHERE client_id = p_client_id
           AND (end_date IS NULL OR end_date > CURRENT_DATE)),
        0
       );
END;
$$;