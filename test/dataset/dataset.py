from google_drive_downloader import GoogleDriveDownloader as gdd

if __name__ == '__main__':
    link = "https://drive.google.com/open?id=1Ovo8PUMEVwWyyT2rsKjtxiOcwnVVklw7"
    gdd.download_file_from_google_drive(file_id='1Ovo8PUMEVwWyyT2rsKjtxiOcwnVVklw7',
                                        dest_path='./data/spectf.csv')
