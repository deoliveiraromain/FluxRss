package fr.deoliveira.fluxrss.app.utils;

import android.os.AsyncTask;
import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.RootElement;
import android.util.Xml;
import at.theengine.android.simple_rss2_android.FeedParser;
import at.theengine.android.simple_rss2_android.RSSItem;
import at.theengine.android.simple_rss2_android.SimpleFeedParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Romain De Oliveira
 *         Fork de SimpleRssParser pour utilisation avec une interface de callBack plus pratique.
 *         Permet de gérer le parsing de plusieurs liens en même temps.
 */
public class SimpleRssParserExt extends SimpleFeedParser {

    private SimpleRssParserExtCallBack mCallback;
    private String url;
    private boolean endList;

    public SimpleRssParserExt(String feedUrl, SimpleRssParserExtCallBack callback, boolean endList) {
        super(feedUrl);
        this.url = feedUrl;
        this.mCallback = callback;
        this.endList = endList;
    }

    public void parseAsync() {
        /**AsyncTask task = new AsyncTask() {
         private Exception mEx;
         private List<RSSItem> items;

         protected void onPostExecute(Object result) {
         if (this.mEx != null) {
         if (SimpleRssParserExt.this.mCallback != null) {
         SimpleRssParserExt.this.mCallback.onError(this.mEx);
         }
         } else if (SimpleRssParserExt.this.mCallback != null) {
         SimpleRssParserExt.this.mCallback.onFeedParsed(this.items);
         }

         }

         protected Object doInBackground(Object... arg0) {
         try {
         this.items = SimpleRssParserExt.this.parse();
         } catch (Exception var3) {
         this.mEx = var3;
         }

         return null;
         }
         };
         task.execute(new Object[0]);
         **/
        AsyncTaskRSS task = new AsyncTaskRSS(this, this.mCallback, this.url, this.endList);
        task.execute();
    }

    private static class AsyncTaskRSS extends AsyncTask<Object, String, Object> {

        private FeedParser feedParser;
        private SimpleRssParserExtCallBack callBack;
        private Exception mEx;
        private List<RSSItem> items;
        private String url;
        private boolean endList;

        private AsyncTaskRSS(FeedParser feedParser, SimpleRssParserExtCallBack callBack, String url, boolean endList) {
            this.feedParser = feedParser;
            this.callBack = callBack;
            this.url = url;
            this.endList = endList;
        }

        @Override
        protected Object doInBackground(Object... params) {
            try {
                this.items = this.feedParser.parse();
                return items;
            } catch (Exception var3) {
                this.mEx = var3;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object object) {
            if (this.mEx != null) {
                if (callBack != null) {
                    callBack.onError(this.mEx);
                }
            } else if (callBack != null) {
                callBack.onFeedParsed(this.items, this.url, this.endList);
            }

        }
    }

    @Override
    public List<RSSItem> parse() {
        final RSSItem currentMessage = new RSSItem();
        RootElement root = new RootElement("rss");
        final ArrayList messages = new ArrayList();
        Element channel = root.getChild("channel");
        Element item = channel.getChild("item");
        item.setEndElementListener(new EndElementListener() {
            public void end() {
                messages.add(currentMessage.copy());
            }
        });
        item.getChild("title").setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentMessage.setTitle(body);
            }
        });
        item.getChild("link").setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentMessage.setLink(body);
            }
        });
        item.getChild("description").setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentMessage.setDescription(body);
            }
        });
        item.getChild("http://purl.org/rss/1.0/modules/content/", "encoded").setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentMessage.setContent(body);
            }
        });
        item.getChild("content").setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentMessage.setContent(body);
            }
        });
        item.getChild("pubDate").setEndTextElementListener(new EndTextElementListener() {
            public void end(String body) {
                currentMessage.setDate(body);
            }
        });

        try {
            Xml.parse(this.getInputStream(), Xml.Encoding.UTF_8, root.getContentHandler());
            return messages;
        } catch (Exception var7) {
            throw new RuntimeException(var7);
        }
    }

    public interface SimpleRssParserExtCallBack {
        void onFeedParsed(List<RSSItem> rssItemList, String urlSource, boolean endList);

        void onError(Exception var1);

    }
}
