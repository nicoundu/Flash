package cl.pingon.flash.views.main.finder;

public interface FinderCallback {

        void error(String error);
        void succes();
        void notFound();

}
