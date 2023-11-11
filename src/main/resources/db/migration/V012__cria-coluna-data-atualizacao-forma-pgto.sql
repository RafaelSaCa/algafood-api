alter Table forma_pagamento add data_atualizacao DATETIME null;
update forma_pagamento set data_atualizacao = UTC_TIMESTAMP;
ALTER Table forma_pagamento MODIFY data_atualizacao DATETIME not null;