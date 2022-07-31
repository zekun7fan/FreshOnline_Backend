
local fail_ids_str = ""
local size = 0
for _ in pairs(KEYS) do size = size + 1 end




local fail_cnt = 0
-- local storage = 0


for i = 1, size, 1
do
    local storage = redis.call("GET", KEYS[i])
    if tonumber(storage) < tonumber(ARGV[i]) then
        fail_cnt = fail_cnt + 1
        fail_ids_str = fail_ids_str .. KEYS[i]
        fail_ids_str = fail_ids_str .. ","
    end
end

if fail_cnt == 0 then
    for i = 1, size, 1
    do
        local storage = redis.call("GET", KEYS[i])
        redis.call("SET", KEYS[i], (tonumber(storage) - tonumber(ARGV[i])))
    end
end

return fail_ids_str
