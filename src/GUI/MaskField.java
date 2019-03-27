package GUI;

import javafx.scene.control.TextField;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;


/**
 * Essa clase possui metodos os quais colocam mascaras nos TextFiel, para
 * modificar a mascara e necessário chamar a função setMaskType passando por
 * parametro o time de mascara o tipo da variavel deve ser trocado para
 * MaskField e também no XML deve ser importado esta classe e modificado os
 * campos TextField que deseja incluir a mascara para MaskFiedl
 *
 * @author Marco
 */
public class MaskField extends TextField {

    private Type maskTipe;

    //Nessa string e colocado os tipos de mascaras aceitas pela classe.
//    private final String tiposAceitos = "Date, Real, Number, Text, Default",;
    public enum Type {

        DAY(1, "Number"), DEFAULT(0, "Default"), HOUR(2, "Hour");
        private int code;
        private String name;

        Type(int code, String name) {
            this.code = code;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public int getCode() {
            return code;
        }

        @Override
        public String toString() {
            return this.name;
        }
    }

    /**
     * Toda clase é inicializada com a mascara Default ou seja sem mascara.
     */
    public MaskField() {
        this.maskTipe = Type.DEFAULT;
    }

    /**
     * Este metodo modifica a mascara a ser utilizada pelo campo. Tipos aceitos:
     * Date, Real, Number, Text, Default.
     *
     * @param type tipo da mascara so sera modificado se for um tipo de
     *             mascara valido.
     */
    public void setMaskType(Type type) {
        this.maskTipe = type;
    }

    @Override
    /**
     * Este método e responsavel pela inserssão dos elementos digitados no campo
     * MaskField eaqui e tratado as entradas em função da mascara ativa.
     *
     */
    public void replaceText(int start, int end, String string) {
        switch (this.maskTipe) {
            case DAY:
                if (string.length() == 0) {
                    super.replaceText(start, end, string);
                    return;
                }
                if (string.matches("[0-9]")) {
                    String s = this.getText() + string;
                    if (new Integer(s) <= 31) {

                        super.replaceText(start, end, string);
                    }
                }

                break;
            case DEFAULT:
                super.replaceText(start, end, string);
                break;
            case HOUR:
                if (string.length() == 0) {
                    super.replaceText(start, end, string);
                    return;
                }
                if (string.matches("[0-9]")) {
                    String s = this.getText() + ((this.getText().length() == 2) ? ":" : "") + string;
                    if (this.getText().length() < 5) {
                        System.out.println(s);
                        boolean teste = true;
                        String splited[] = s.split(":");
                        if (new Integer(splited[0]) > 23) {
                            teste = false;
                        }
                        if (s.length() > 4 && new Integer(splited[1]) > 59) {
                            teste = false;
                        }
                        if (teste) {
                            super.replaceText(0, end, s);
                        }
                    }
                }
                break;
            default:
                super.replaceText(start, end, string);
                break;

        }
    }
}
