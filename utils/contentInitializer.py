import os
import shutil

def deleteFile(path):
    try:
        os.remove(path)
    except IsADirectoryError:
        shutil.rmtree(path)
    except Exception as e:
        print("Error in deleting ",path,e)
        print()

deleteFile('../content/ecommerce.trace.db')
deleteFile('../content/ecommerce.mv.db')
deleteFile('../content/ecommerce/addins/skyve-content')
deleteFile('../content/ecommerce/addins/skyve-content.zip')
deleteFile('../content/ecommerce/SKYVE_CONTENT')
deleteFile('../content/ecommerce/SKYVE_CACHE')


