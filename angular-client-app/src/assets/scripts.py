import json

with open("books.json", 'r') as file:
    contents = json.load(file)

genreLablesList = ['tale', 'poem', 'story', 'folklore', 
          'novel', 'tragedy', 'gothic', 'fiction', 
          'social', 'realism', 'political', 'satire', 
          'modern', 'autobiography', 'saga', 'epic', 'essay', 
          'post-apocalyptic', 'dystopia', 'Bildungsroman', 'Monogatari']

import requests
from bs4 import BeautifulSoup

genre_dict = []

import re

for i in range(len(contents)):
    
    res = requests.get(contents[i]['link'][:-1])
    soup = BeautifulSoup(res.text, 'html.parser')
    
    if(contents[i]['link'][:-1].find('en.wikipedia.org') != -1):
        infobox_data = soup.select("td.infobox-data")
        infobox_labels = soup.select("th.infobox-label")
        info_text = ""
        size_taken = 1
        while(len(info_text.split()) <= 30 and size_taken <= 10):
            body = [Soup for Soup in soup.select("div.mw-content-ltr.mw-parser-output p") if Soup.text != '\n']
            print('@'*100)
            print(body)
            print('$'*100)
            info_text = ''.join([ss if (ss.startswith('.') or ss.startswith(')') or ss.startswith(',') or ss.startswith(';') ) else ' '+ss for ss in [s for s in body[0].stripped_strings if not(re.search(r'\[([0-9]+)|([a-z]+)\]', s))]])
            size_taken = size_taken + 1  
        
        LabelSet = False
        
        for j in range(len(infobox_labels)):
            if(infobox_labels[j].text == 'Genre'):
                LabelSet = True
                string = infobox_data[j].stripped_strings
                print('~'*80)
                print(contents[i]['link'])
                print([s for s in body[0].stripped_strings if not(re.search(r'\[([0-9]+)|([a-z]+)\]', s))])
                print('-'*80)
                unwanted_chars = "()/,"

                ss = [''.join(c for c in s if c not in list(unwanted_chars)) for s in string if s not in list(unwanted_chars)]
                labels = list(set(l for l in genreLablesList for str in ss if (str.upper().find(l.upper()) != -1) 
                            or (str == 'Autobiographical novel' and l == 'autobiography')
                            or (str == 'Poetry' and l == 'poem')
                            or (str == 'Satirical novel' and l == 'satire')
                            ))
                
                contents[i]['genre'] = ss
                contents[i]['labels'] = labels
        
        if(not LabelSet):
            contents[i]['genre'] = ['UnKnown']
            contents[i]['labels'] = ['UnKnown']
            
        contents[i]['information'] = info_text
    else:
        print([Soup for Soup in soup.select("div.c-userContent p") if Soup.text != '\n'])
        info_text = ""
        size_taken = 1
        
        while(len(info_text.split()) <= 30 and size_taken <= 10):
            body = [Soup for Soup in soup.select("div.c-userContent p") if Soup.text != '\n'][:size_taken]
            info_text = ''.join([ss if ss.endswith(' ') else ss+' ' for ss in [s for s in body[0].stripped_strings]])
            size_taken = size_taken + 1 

        contents[i]['information'] = info_text
        contents[i]['genre'] = ['Poetry']
        contents[i]['labels'] = ['poem']
        
    genre_dict.append(contents[i])
            
    print(genre_dict[i])
            
            
with open('genre.json', 'w') as outfile:
    json.dump(genre_dict, outfile)