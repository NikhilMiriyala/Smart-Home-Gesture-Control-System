import os
from flask import Flask
import flask


app = Flask(__name__)
app.secret_key = 'password'

@app.route('/', methods = ['GET', 'POST'])
def handle_request():
    files_ids = list(flask.request.files)
    for file_id in files_ids:
        imagefile = flask.request.files[file_id]
        filename = imagefile.filename
        save_directory = "/Users/nikhilmiriyala/Desktop/MC"
        imagefile.save(os.path.join(save_directory,filename))
    return "Video Uploaded Successfully"

if __name__ =="__main__":
	app.run(debug=True, host="0.0.0.0")