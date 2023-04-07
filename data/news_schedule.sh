#!/bin/sh
echo "start"
python3 ~/data/crawling/news_crawling.py
python3 ~/data/hadoop/word_mapping.py
python3 ~/data/hadoop/data_main.py
echo "end"
