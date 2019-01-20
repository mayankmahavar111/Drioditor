from google_drive_downloader import GoogleDriveDownloader as gdd

if __name__ == '__main__':
    link = "https://drive.google.com/open?id=0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N"
    gdd.download_file_from_google_drive(file_id='0B4B1pQQ3lXI_bjNUdFQ1RWlzU01lUU9FN0V6SXhSWE1KTm1N',
                                        dest_path='./data/iris.csv')
