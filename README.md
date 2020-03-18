# WebsiteDeployer

A simple tool to upload a website to an S3 Bucket that has web hosting enabled.
It makes all the files uploaded public.

To use, just change the config.properties file in the resources folder.
You need to add in your access key and secret key, and then change the website name to your bucket name,
and change the folder to point to where your files are on disk.

It will ask for confirmation before running.
This program deletes anything current existing in that S3 bucket.
