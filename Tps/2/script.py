import gzip
import shutil
from nbt import nbt

input_path = "level.dat"
output_path = "level_fixed.dat"
temp_path = "level_decompressed.dat"

with gzip.open(input_path, "rb") as f_in:
    with open(temp_path, "wb") as f_out:
        shutil.copyfileobj(f_in, f_out)

nbtfile = nbt.NBTFile(temp_path, 'rb')

if "Data" in nbtfile:
    data_tag = nbtfile["Data"]
    if "DataPacks" in data_tag:
        del data_tag["DataPacks"]

nbtfile.write_file(temp_path)

with open(temp_path, "rb") as f_in:
    with gzip.open(output_path, "wb") as f_out:
        shutil.copyfileobj(f_in, f_out)

print("✅ level_fixed.dat criado com sucesso.")
